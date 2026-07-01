// /resources/js/hospital-location.js

document.addEventListener("DOMContentLoaded", function() {
    const mapContainer = document.getElementById("hospitalMap");

    if (!mapContainer || !window.kakao || !window.kakao.maps) {
        return;
    }

    function initMap() {
        const latitude = Number(mapContainer.dataset.latitude);
        const longitude = Number(mapContainer.dataset.longitude);
        const placeName = mapContainer.dataset.placeName || "KMCH 한국중앙병원";
        const address = mapContainer.dataset.address || "";

        if (Number.isNaN(latitude) || Number.isNaN(longitude)) {
            return;
        }

        // 학습용 가상 병원 위치입니다. 실제 병원 주소와 무관합니다.
        const hospitalPosition = new window.kakao.maps.LatLng(latitude, longitude);

        mapContainer.classList.add("is-map-loaded");
        mapContainer.replaceChildren();

        const map = new window.kakao.maps.Map(mapContainer, {
            center: hospitalPosition,
            level: 3
        });

        const marker = new window.kakao.maps.Marker({
            map: map,
            position: hospitalPosition
        });

        const infoWindow = new window.kakao.maps.InfoWindow({
            content: `<div class="hospital-map-info"><strong>${placeName}</strong><span>${address}</span></div>`
        });

        infoWindow.open(map, marker);
    }

    if (typeof window.kakao.maps.load === "function") {
        window.kakao.maps.load(initMap);
        return;
    }

    initMap();
});
