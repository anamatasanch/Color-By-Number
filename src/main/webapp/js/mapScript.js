var mapIndex = 1;
showMaps(mapIndex);

// Next/previous controls
function plusMaps(nextMap) {
    showMaps(mapIndex += nextMap);
}

// Thumbnail image controls
function currentSlide(nextMap) {
    showMaps(mapIndex = nextMap);
}

function showMaps(nextMap) {
    var index;
    var maps = document.getElementsByClassName("allMaps");
    if (nextMap > maps.length) {
        mapIndex = 1;
    }
    if (nextMap < 1) {
        mapIndex = maps.length;
    }
    for (index = 0; index < maps.length; index++) {
        maps[index].style.display = "none";
    }

    maps[mapIndex - 1].style.display = "block";
}
