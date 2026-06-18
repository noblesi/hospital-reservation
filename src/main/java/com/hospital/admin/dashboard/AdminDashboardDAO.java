package com.hospital.admin.dashboard;

import com.hospital.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardDAO {
    private static final String[] WEEKDAY_LABELS = {"월", "화", "수", "목", "금", "토", "일"};

    public AdminDashboardSummaryDTO selectDashboardSummary() throws SQLException {
        AdminDashboardSummaryDTO summary = new AdminDashboardSummaryDTO();
        summary.setTotalAppointmentCount(selectCount("SELECT COUNT(*) FROM APPOINTMENT"));
        summary.setCompletedTreatmentCount(selectCount(
                "SELECT COUNT(*) FROM APPOINTMENT WHERE STATUS IN ('COMPLETED', 'DONE', '진료완료')"
        ));
        return summary;
    }

    public List<AdminDashboardChartDTO> selectMonthlyAppointmentStatus() throws SQLException {
        String sql = "SELECT TO_NUMBER(TO_CHAR(APPOINTMENT_DATE, 'MM')) CHART_KEY, COUNT(*) CNT "
                + "FROM APPOINTMENT "
                + "WHERE APPOINTMENT_DATE >= TRUNC(SYSDATE, 'YYYY') "
                + "GROUP BY TO_NUMBER(TO_CHAR(APPOINTMENT_DATE, 'MM'))";
        Map<Integer, Integer> countMap = selectChartCountMap(sql);
        int maxCount = getMaxCount(countMap);
        List<AdminDashboardChartDTO> chartList = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            int count = countMap.getOrDefault(month, 0);
            chartList.add(new AdminDashboardChartDTO(month + "월", count, calculateRate(count, maxCount)));
        }

        return chartList;
    }

    public List<AdminDashboardChartDTO> selectWeekdayAppointmentStatus() throws SQLException {
        String sql = "SELECT TRUNC(APPOINTMENT_DATE) - TRUNC(APPOINTMENT_DATE, 'IW') + 1 CHART_KEY, "
                + "COUNT(*) CNT "
                + "FROM APPOINTMENT "
                + "WHERE APPOINTMENT_DATE >= TRUNC(SYSDATE, 'YYYY') "
                + "GROUP BY TRUNC(APPOINTMENT_DATE) - TRUNC(APPOINTMENT_DATE, 'IW') + 1";
        Map<Integer, Integer> countMap = selectChartCountMap(sql);
        int maxCount = getMaxCount(countMap);
        List<AdminDashboardChartDTO> chartList = new ArrayList<>();

        for (int weekday = 1; weekday <= WEEKDAY_LABELS.length; weekday++) {
            int count = countMap.getOrDefault(weekday, 0);
            chartList.add(new AdminDashboardChartDTO(
                    WEEKDAY_LABELS[weekday - 1],
                    count,
                    calculateRate(count, maxCount)
            ));
        }

        return chartList;
    }

    private Map<Integer, Integer> selectChartCountMap(String sql) throws SQLException {
        Map<Integer, Integer> countMap = new HashMap<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                countMap.put(rs.getInt("CHART_KEY"), rs.getInt("CNT"));
            }
        }

        return countMap;
    }

    private int getMaxCount(Map<Integer, Integer> countMap) {
        int maxCount = 0;

        for (Integer count : countMap.values()) {
            if (count != null && count > maxCount) {
                maxCount = count;
            }
        }

        return maxCount;
    }

    private int calculateRate(int count, int maxCount) {
        if (maxCount <= 0 || count <= 0) {
            return 0;
        }

        return Math.max(4, (int) Math.round((double) count / maxCount * 100));
    }

    private int selectCount(String sql) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
