window.addEventListener("load", fetchData)

const ps = document.querySelectorAll('#system-info .list-item p');
const namesContainer = document.querySelectorAll(".names")[0];
const endpoints = [ "/ads/getAllAdsCount", 
                    "/users/getAllUsersCount", 
                    "/reports/getAllReportsCount", 
                    "/users/getAllBannedUsersCount", 
                    "/users/getUsersCountByRole/MODERATOR"];

function fetchData() {
    for(let i = 0; i < ps.length; i++) {
        fetchFunction(endpoints[i], ps[i]);
    }

    fetch('/users/getModerators')
    .then(res => res.json())
    .then(names => {
        names.forEach(name => {
            let div = document.createElement("div");
            div.className = "item";

            let p = document.createElement("p");
            p.className = "moderator-name";
            p.innerText = name;

            let i = document.createElement("i");
            i.className = "fa-solid fa-user-pen";
            div.appendChild(i);
            div.appendChild(p);
            namesContainer.appendChild(div);
        });
    }).catch(e => console.log(e));
}

function fetchFunction(endpoint, p) {
    fetch(endpoint)
    .then(res => res.json())
    .then(info => {
        p.innerText = info;
    }).catch(e => console.log(e));
}