package com.hospital.common.util;

/**
 * 목록 화면의 페이징 계산을 공통으로 처리하는 유틸리티 클래스입니다.
 *
 * DAO에서 Oracle {ROW_NUMBER()} 또는 {ROWNUM} 범위를 사용할 때 필요한
 * {startNum}, {endNum}과 JSP 페이지 네비게이션에 필요한 시작/끝 페이지를
 * 한 번에 계산합니다.
 */
public final class PaginationUtil {

    public static final int DEFAULT_CURRENT_PAGE = 1;
    public static final int DEFAULT_PAGE_SCALE = 10;
    public static final int DEFAULT_BLOCK_SCALE = 10;

    /**
     * 유틸리티 클래스의 인스턴스 생성을 막는 생성자입니다.
     */
    private PaginationUtil() {
    }

    /**
     * 기본 페이지 크기와 기본 페이지 블록 크기로 페이징 정보를 계산하는 메서드입니다.
     */
    public static Pagination create(int currentPage, int totalCount) {
        return create(currentPage, totalCount, DEFAULT_PAGE_SCALE, DEFAULT_BLOCK_SCALE);
    }

    /**
     * 지정한 페이지 크기와 기본 페이지 블록 크기로 페이징 정보를 계산하는 메서드입니다.
     */
    public static Pagination create(int currentPage, int totalCount, int pageScale) {
        return create(currentPage, totalCount, pageScale, DEFAULT_BLOCK_SCALE);
    }

    /**
     * 화면 목록과 페이지 네비게이션에 필요한 모든 페이징 정보를 계산하는 메서드입니다.
     */
    public static Pagination create(int currentPage, int totalCount, int pageScale, int blockScale) {
        int safeTotalCount = Math.max(totalCount, 0);
        int safePageScale = normalizePositive(pageScale, DEFAULT_PAGE_SCALE);
        int safeBlockScale = normalizePositive(blockScale, DEFAULT_BLOCK_SCALE);
        int totalPage = calculateTotalPage(safeTotalCount, safePageScale);
        int safeCurrentPage = normalizeCurrentPage(currentPage, totalPage);

        int startNum = calculateStartNum(safeCurrentPage, safePageScale);
        int endNum = calculateEndNum(safeCurrentPage, safePageScale);
        int startPage = calculateStartPage(safeCurrentPage, safeBlockScale);
        int endPage = Math.min(startPage + safeBlockScale - 1, totalPage);

        return new Pagination(
                safeCurrentPage,
                safeTotalCount,
                safePageScale,
                safeBlockScale,
                totalPage,
                startNum,
                endNum,
                startPage,
                endPage
        );
    }

    /**
     * DAO 조회 범위의 시작 번호를 계산하는 메서드입니다.
     */
    public static int calculateStartNum(int currentPage, int pageScale) {
        int safeCurrentPage = Math.max(currentPage, DEFAULT_CURRENT_PAGE);
        int safePageScale = normalizePositive(pageScale, DEFAULT_PAGE_SCALE);
        return (safeCurrentPage - 1) * safePageScale + 1;
    }

    /**
     * DAO 조회 범위의 끝 번호를 계산하는 메서드입니다.
     */
    public static int calculateEndNum(int currentPage, int pageScale) {
        int safeCurrentPage = Math.max(currentPage, DEFAULT_CURRENT_PAGE);
        int safePageScale = normalizePositive(pageScale, DEFAULT_PAGE_SCALE);
        return safeCurrentPage * safePageScale;
    }

    /**
     * 전체 데이터 수와 페이지 크기로 전체 페이지 수를 계산하는 메서드입니다.
     */
    public static int calculateTotalPage(int totalCount, int pageScale) {
        int safeTotalCount = Math.max(totalCount, 0);
        int safePageScale = normalizePositive(pageScale, DEFAULT_PAGE_SCALE);

        if (safeTotalCount == 0) {
            return 1;
        }

        return (int) Math.ceil((double) safeTotalCount / safePageScale);
    }

    /**
     * 현재 페이지 번호를 유효한 페이지 범위 안으로 보정하는 메서드입니다.
     */
    public static int normalizeCurrentPage(int currentPage, int totalPage) {
        int safeTotalPage = Math.max(totalPage, 1);

        if (currentPage < DEFAULT_CURRENT_PAGE) {
            return DEFAULT_CURRENT_PAGE;
        }

        return Math.min(currentPage, safeTotalPage);
    }

    /**
     * 양수가 아닌 값에 기본값을 적용하는 메서드입니다.
     */
    public static int normalizePositive(int value, int defaultValue) {
        if (value > 0) {
            return value;
        }

        return defaultValue > 0 ? defaultValue : DEFAULT_PAGE_SCALE;
    }

    /**
     * 현재 페이지가 속한 페이지 블록의 시작 페이지를 계산하는 메서드입니다.
     */
    private static int calculateStartPage(int currentPage, int blockScale) {
        int safeCurrentPage = Math.max(currentPage, DEFAULT_CURRENT_PAGE);
        int safeBlockScale = normalizePositive(blockScale, DEFAULT_BLOCK_SCALE);
        return ((safeCurrentPage - 1) / safeBlockScale) * safeBlockScale + 1;
    }

    /**
     * 페이징 계산 결과를 담는 불변 객체입니다.
     */
    public static final class Pagination {
        private final int currentPage;
        private final int totalCount;
        private final int pageScale;
        private final int blockScale;
        private final int totalPage;
        private final int startNum;
        private final int endNum;
        private final int startPage;
        private final int endPage;

        /**
         * 계산된 페이징 값을 불변 객체로 초기화하는 생성자입니다.
         */
        private Pagination(
                int currentPage,
                int totalCount,
                int pageScale,
                int blockScale,
                int totalPage,
                int startNum,
                int endNum,
                int startPage,
                int endPage
        ) {
            this.currentPage = currentPage;
            this.totalCount = totalCount;
            this.pageScale = pageScale;
            this.blockScale = blockScale;
            this.totalPage = totalPage;
            this.startNum = startNum;
            this.endNum = endNum;
            this.startPage = startPage;
            this.endPage = endPage;
        }

        /**
         * 현재 페이지 번호를 반환하는 메서드입니다.
         */
        public int getCurrentPage() {
            return currentPage;
        }

        /**
         * 전체 데이터 수를 반환하는 메서드입니다.
         */
        public int getTotalCount() {
            return totalCount;
        }

        /**
         * 한 페이지에 보여줄 데이터 수를 반환하는 메서드입니다.
         */
        public int getPageScale() {
            return pageScale;
        }

        /**
         * 한 번에 보여줄 페이지 번호 수를 반환하는 메서드입니다.
         */
        public int getBlockScale() {
            return blockScale;
        }

        /**
         * 전체 페이지 수를 반환하는 메서드입니다.
         */
        public int getTotalPage() {
            return totalPage;
        }

        /**
         * DAO 조회 범위의 시작 번호를 반환하는 메서드입니다.
         */
        public int getStartNum() {
            return startNum;
        }

        /**
         * DAO 조회 범위의 끝 번호를 반환하는 메서드입니다.
         */
        public int getEndNum() {
            return endNum;
        }

        /**
         * 페이지 블록의 시작 페이지를 반환하는 메서드입니다.
         */
        public int getStartPage() {
            return startPage;
        }

        /**
         * 페이지 블록의 끝 페이지를 반환하는 메서드입니다.
         */
        public int getEndPage() {
            return endPage;
        }

        /**
         * 이전 페이지 블록이 있는지 확인하는 메서드입니다.
         */
        public boolean hasPreviousBlock() {
            return startPage > 1;
        }

        /**
         * 다음 페이지 블록이 있는지 확인하는 메서드입니다.
         */
        public boolean hasNextBlock() {
            return endPage < totalPage;
        }

        /**
         * 이전 페이지 블록으로 이동할 페이지 번호를 반환하는 메서드입니다.
         */
        public int getPreviousBlockPage() {
            return hasPreviousBlock() ? startPage - 1 : DEFAULT_CURRENT_PAGE;
        }

        /**
         * 다음 페이지 블록으로 이동할 페이지 번호를 반환하는 메서드입니다.
         */
        public int getNextBlockPage() {
            return hasNextBlock() ? endPage + 1 : totalPage;
        }

        /**
         * 현재 페이지가 첫 페이지인지 확인하는 메서드입니다.
         */
        public boolean isFirstPage() {
            return currentPage == DEFAULT_CURRENT_PAGE;
        }

        /**
         * 현재 페이지가 마지막 페이지인지 확인하는 메서드입니다.
         */
        public boolean isLastPage() {
            return currentPage == totalPage;
        }
    }
}
