window.addEventListener("load", fetchImage)

const categoriesSection = document.getElementById('categories');

function fetchImage() {
    fetch("http://localhost:8080/ads/getImages/2")
    .then(img => img.json())
    .then(img => console.log(img))
    .then(imgSrc => {
        let img = document.querySelector("img");
        img.src = imgSrc;
    })
    /*.then(array => array.forEach(element => {
       const div = document.createElement("div");
       div.className = "category";

       const a = document.createElement('a');

       console.log(element.name);
       a.innerHTML = `<a th:href = '@{/ads/ad_by_category/{category-name}(category-name=${element.name})}'>${element.name}</a>`
    
       div.appendChild(a);
       categoriesSection.appendChild(div);
    }))
    .catch(e => console.log(e));*/
}