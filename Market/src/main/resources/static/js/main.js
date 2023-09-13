window.addEventListener("load", fetchCategories)

const categoriesSection = document.getElementById('categories');

function fetchCategories() {
    fetch("http://localhost:8080/categories/getAll")
    .then(res => res.json())
    .then(array => array.forEach(element => {
       const div = document.createElement("div");
       div.className = "category";

       const a = document.createElement('a');

       console.log(element.name);
       a.innerHTML = `<a th:href = '@{/ads/ad_by_category/{category-name}(category-name=${element.name})}'>${element.name}</a>`
    
       div.appendChild(a);
       categoriesSection.appendChild(div);
    }))
    .catch(e => console.log(e));
}