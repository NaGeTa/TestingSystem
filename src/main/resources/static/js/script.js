document.addEventListener("DOMContentLoaded", function () {
    var dropdown = document.querySelector(".dropdown");
    var dropdownContent = document.querySelector(".dropdown-content");

    dropdown.addEventListener("click", function () {
        dropdownContent.style.display = dropdownContent.style.display === "block" ? "none" : "block";
    });

    document.addEventListener("click", function (event) {
        var targetElement = event.target; // элемент, на который был выполнен клик

        // Проверяем, является ли цель клика рабочей областью меню
        if (!dropdown.contains(targetElement)) {
            // Если нет, скрываем меню
            dropdownContent.style.display = "none";
        }
    });
});