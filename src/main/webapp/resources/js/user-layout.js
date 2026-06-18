// /resources/js/user-layout.js

document.addEventListener("DOMContentLoaded", function () {
    const menuBtn = document.querySelector(".menu-btn");
    const searchBtn = document.querySelector(".search-btn");
    const menuPanel = document.querySelector("#userAllMenu");
    const searchPanel = document.querySelector("#userSearchPanel");
    const menuCloseBtn = document.querySelector(".menu-close-btn");
    const searchCloseBtn = document.querySelector(".search-close-btn");
    const searchInput = document.querySelector("#userHeaderKeyword");

    function setPanel(panel, button, isOpen) {
        if (!panel || !button) {
            return;
        }

        panel.hidden = !isOpen;
        button.setAttribute("aria-expanded", String(isOpen));
    }

    function closeMenu() {
        setPanel(menuPanel, menuBtn, false);
    }

    function closeSearch() {
        setPanel(searchPanel, searchBtn, false);
    }

    function openMenu() {
        closeSearch();
        setPanel(menuPanel, menuBtn, true);
    }

    function openSearch() {
        closeMenu();
        setPanel(searchPanel, searchBtn, true);

        if (searchInput) {
            searchInput.focus();
        }
    }

    if (menuBtn && menuPanel) {
        menuBtn.addEventListener("click", function () {
            if (menuPanel.hidden) {
                openMenu();
            } else {
                closeMenu();
            }
        });
    }

    if (searchBtn && searchPanel) {
        searchBtn.addEventListener("click", function () {
            if (searchPanel.hidden) {
                openSearch();
            } else {
                closeSearch();
            }
        });
    }

    if (menuCloseBtn) {
        menuCloseBtn.addEventListener("click", closeMenu);
    }

    if (searchCloseBtn) {
        searchCloseBtn.addEventListener("click", closeSearch);
    }

    document.addEventListener("keydown", function (event) {
        if (event.key === "Escape") {
            closeMenu();
            closeSearch();
        }
    });

    document.addEventListener("click", function (event) {
        const target = event.target;
        const isMenuClick = menuPanel && (menuPanel.contains(target) || (menuBtn && menuBtn.contains(target)));
        const isSearchClick = searchPanel && (searchPanel.contains(target) || (searchBtn && searchBtn.contains(target)));

        if (!isMenuClick) {
            closeMenu();
        }

        if (!isSearchClick) {
            closeSearch();
        }
    });
});
