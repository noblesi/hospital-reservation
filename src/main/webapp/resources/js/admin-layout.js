// /resources/js/admin-layout.js

document.addEventListener("DOMContentLoaded", function () {
    const logoutBtn = document.querySelector(".logout-btn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (event) {
            const isConfirm = confirm("로그아웃 하시겠습니까?");

            if (!isConfirm) {
                event.preventDefault();
            }
        });
    }
});
