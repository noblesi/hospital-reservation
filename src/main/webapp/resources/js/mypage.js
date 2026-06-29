/**
 * 마이페이지 공통 스크립트
 * 예약/진료 모달, 비밀번호 확인 모달, 상태 필터,
 * 생년월일 선택 및 카카오 우편번호 검색을 초기화한다.
 */
(function () {
    "use strict";

    /**
     * DB 상태값의 앞뒤 또는 중간 공백을 제거해 필터 비교값을 통일한다.
     *
     * @param {string} status 예약 상태값
     * @returns {string} 공백이 제거된 상태값
     */
    function normalizeStatus(status) {
        return (status || "").replace(/\s/g, "");
    }

    /**
     * 모달 열기와 닫기 동작을 공통으로 연결한다.
     *
     * @param {Object} options 모달 요소 선택자 설정
     */
    function bindModal(options) {
        const openButton = document.getElementById(options.openButtonId);
        const modal = document.getElementById(options.modalId);

        if (!openButton || !modal) {
            return;
        }

        const closeButtons = modal.querySelectorAll(options.closeSelector);
        const firstCloseButton = modal.querySelector(options.focusSelector);

        function openModal(event) {
            if (event) {
                event.preventDefault();
            }

            modal.classList.add("open");
            modal.setAttribute("aria-hidden", "false");
            openButton.setAttribute("aria-expanded", "true");
            document.body.classList.add("modalOpen");

            if (firstCloseButton) {
                firstCloseButton.focus();
            }
        }

        function closeModal() {
            modal.classList.remove("open");
            modal.setAttribute("aria-hidden", "true");
            openButton.setAttribute("aria-expanded", "false");
            document.body.classList.remove("modalOpen");

            if (options.resetSelector) {
                const resetTarget = modal.querySelector(options.resetSelector);

                if (resetTarget) {
                    resetTarget.value = "";
                }
            }

            openButton.focus();
        }

        openButton.addEventListener("click", openModal);

        closeButtons.forEach(function (button) {
            button.addEventListener("click", closeModal);
        });

        modal.addEventListener("click", function (event) {
            if (event.target === modal) {
                closeModal();
            }
        });

        document.addEventListener("keydown", function (event) {
            if (event.key === "Escape" && modal.classList.contains("open")) {
                closeModal();
            }
        });

        if (modal.dataset.autoOpen === "true") {
            openModal();
        }
    }

    /**
     * 예약 상태 탭과 예약 행을 연결한다.
     */
    function bindReservationFilter() {
        const modal = document.getElementById("reservationModal");

        if (!modal) {
            return;
        }

        const tabs = modal.querySelectorAll("[data-status-filter]");
        const rows = modal.querySelectorAll("[data-reservation-status]");
        const emptyRow = document.getElementById("filteredReservationEmpty");

        tabs.forEach(function (tab) {
            tab.addEventListener("click", function () {
                const selectedStatus = normalizeStatus(tab.dataset.statusFilter);
                let visibleCount = 0;

                tabs.forEach(function (item) {
                    item.classList.toggle("active", item === tab);
                });

                rows.forEach(function (row) {
                    const rowStatus = normalizeStatus(row.dataset.reservationStatus);
                    const visible = selectedStatus === "all" || rowStatus === selectedStatus;

                    row.hidden = !visible;

                    if (visible) {
                        visibleCount++;
                    }
                });

                if (emptyRow) {
                    emptyRow.hidden = visibleCount !== 0;
                }
            });
        });
    }

    /**
     * 비밀번호 입력값 표시/숨김 버튼을 연결한다.
     */
    function bindPasswordToggle() {
        const toggleButtons = document.querySelectorAll(
            ".passwordToggle, .withdrawalPasswordToggle"
        );

        toggleButtons.forEach(function (toggleButton) {
            const input = toggleButton.parentElement.querySelector('input[type="password"], input[type="text"]');

            if (!input) {
                return;
            }

            toggleButton.addEventListener("click", function () {
                const showPassword = input.type === "password";

                input.type = showPassword ? "text" : "password";
                toggleButton.textContent = showPassword ? "숨김" : "보기";
            });
        });
    }

    /**
     * 연도와 월에 맞는 일 선택지를 생성한다.
     *
     * @param {string} yearId 연도 select ID
     * @param {string} monthId 월 select ID
     * @param {string} dayId 일 select ID
     */
    function bindBirthSelect(yearId, monthId, dayId) {
        const yearSelect = document.getElementById(yearId);
        const monthSelect = document.getElementById(monthId);
        const daySelect = document.getElementById(dayId);

        if (!yearSelect || !monthSelect || !daySelect) {
            return;
        }

        function setDayOptions(selectedDay) {
            const year = Number(yearSelect.value);
            const month = Number(monthSelect.value);

            daySelect.innerHTML = '<option value="">일</option>';

            if (!year || !month) {
                return;
            }

            const lastDay = new Date(year, month, 0).getDate();

            for (let day = 1; day <= lastDay; day++) {
                const value = String(day).padStart(2, "0");
                const option = document.createElement("option");

                option.value = value;
                option.textContent = value;
                option.selected = value === selectedDay;
                daySelect.appendChild(option);
            }
        }

        setDayOptions(daySelect.dataset.selectedDay || "");

        yearSelect.addEventListener("change", function () {
            setDayOptions("");
        });

        monthSelect.addEventListener("change", function () {
            setDayOptions("");
        });
    }

    /**
     * 카카오 우편번호 검색 버튼을 주소 입력란과 연결한다.
     */
    function bindDaumPostcode() {
        const addressButton = document.getElementById("addressSearchButton");

        if (!addressButton) {
            return;
        }

        addressButton.addEventListener("click", function () {
            if (typeof daum === "undefined" || !daum.Postcode) {
                alert("우편번호 서비스를 불러오지 못했습니다.");
                return;
            }

            new daum.Postcode({
                oncomplete: function (data) {
                    const selectedAddress = data.userSelectedType === "R"
                        ? data.roadAddress
                        : data.jibunAddress;

                    document.getElementById("zipCode").value = data.zonecode;
                    document.getElementById("address").value = selectedAddress;
                    document.getElementById("addressDetail").focus();
                }
            }).open();
        });
    }

    /**
     * 예약 취소 폼 제출 전 사용자 확인창을 표시한다.
     */
    function bindReservationCancelConfirm() {
        const cancelForms = document.querySelectorAll(".reservationCancelForm");

        cancelForms.forEach(function (form) {
            form.addEventListener("submit", function (event) {
                if (!confirm("예약을 취소하시겠습니까?")) {
                    event.preventDefault();
                }
            });
        });
    }

    /**
     * 회원정보 수정 시 전화번호를 010-0000-0000 형식으로 입력했는지 확인한다.
     */
    function bindMemberInfoValidation() {
        const form = document.getElementById("memberInfoUpdateForm");
        const phoneNumber = document.getElementById("phoneNumber");
        const phonePattern = /^010-\d{4}-\d{4}$/;

        if (!form || !phoneNumber) {
            return;
        }

        form.addEventListener("submit", function (event) {
            if (!phonePattern.test(phoneNumber.value.trim())) {
                alert("하이픈(-)이 없습니다. 휴대전화번호는 010-0000-0000 형식으로 입력해주세요.");
                phoneNumber.focus();
                event.preventDefault();
            }
        });
    }

    /**
     * 서버 비밀번호 확인 후 최종 회원 탈퇴 확인 모달을 제어한다.
     */
    function bindWithdrawalConfirmModal() {
        const modal = document.getElementById("withdrawalConfirmModal");

        if (!modal) {
            return;
        }

        const closeButton = modal.querySelector("[data-withdrawal-modal-close]");

        function openModal() {
            modal.classList.add("open");
            modal.setAttribute("aria-hidden", "false");
            document.body.classList.add("modalOpen");

            if (closeButton) {
                closeButton.focus();
            }
        }

        function closeModal() {
            // 서버에 저장된 탈퇴 비밀번호 확인값도 함께 폐기한다.
            if (modal.dataset.cancelUrl) {
                location.href = modal.dataset.cancelUrl;
            }
        }

        if (closeButton) {
            closeButton.addEventListener("click", closeModal);
        }

        modal.addEventListener("click", function (event) {
            if (event.target === modal) {
                closeModal();
            }
        });

        document.addEventListener("keydown", function (event) {
            if (event.key === "Escape" && modal.classList.contains("open")) {
                closeModal();
            }
        });

        if (modal.dataset.autoOpen === "true") {
            openModal();
        }
    }

    /* 예약 내역 및 진료 기록 모달 초기화 */
    bindModal({
        openButtonId: "openReservationModal",
        modalId: "reservationModal",
        closeSelector: "[data-modal-close]",
        focusSelector: ".modalCloseIcon"
    });

    bindModal({
        openButtonId: "openMedicalRecordModal",
        modalId: "medicalRecordModal",
        closeSelector: "[data-medical-modal-close]",
        focusSelector: ".modalCloseIcon"
    });

    /* 개인정보 접근 전 비밀번호 확인 모달 초기화 */
    bindModal({
        openButtonId: "openPasswordCheckModal",
        modalId: "passwordCheckModal",
        closeSelector: "[data-password-modal-close]",
        focusSelector: "#infoPassword",
        resetSelector: "#infoPassword"
    });

    bindReservationFilter();
    bindPasswordToggle();
    bindBirthSelect("memberBirthYear", "memberBirthMonth", "memberBirthDay");
    bindBirthSelect("minorBirthYear", "minorBirthMonth", "minorBirthDay");
    bindDaumPostcode();
    bindMemberInfoValidation();
    bindReservationCancelConfirm();
    bindWithdrawalConfirmModal();
})();
