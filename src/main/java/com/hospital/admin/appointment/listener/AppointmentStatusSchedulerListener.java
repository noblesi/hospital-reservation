package com.hospital.admin.appointment.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hospital.admin.appointment.AdminAppointmentService;

// 예약완료 상태 중 예약일이 지난 건을 진료완료로 주기 변경한다.
public class AppointmentStatusSchedulerListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppointmentStatusSchedulerListener.class.getName());
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::completeExpiredAppointments, 0, 1, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    private void completeExpiredAppointments() {
        try {
            int updatedCount = new AdminAppointmentService().completeExpiredAppointments();
            if (updatedCount > 0) {
                LOGGER.info("진료완료로 변경한 지난 예약완료 건수: " + updatedCount);
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "지난 예약완료 상태 변경 작업 실패", e);
        }
    }
}
