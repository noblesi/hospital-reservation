// /resources/js/user-layout.js

document.addEventListener("DOMContentLoaded", function() {
    const menuBtn = document.querySelector(".menu-btn");
    const searchBtn = document.querySelector(".search-btn");

    if (menuBtn) {
        menuBtn.addEventListener("click", function() {
            console.log("전체 메뉴 버튼 클릭");
            // TODO: 전체 메뉴 레이어 또는 사이드 메뉴를 연결하세요.
        });
    }

    if (searchBtn) {
        searchBtn.addEventListener("click", function() {
            console.log("검색 버튼 클릭");
            // TODO: 검색창 열기 기능을 연결하세요.
        });
    }
});
