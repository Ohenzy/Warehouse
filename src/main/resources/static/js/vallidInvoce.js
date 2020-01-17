var products = {
    nameProduct :[],
    quantity : [],
    unit : [],
    price : [],
    type : ''
};

function formSwitch(id) {
    buttonSwitch(id);
    clearProduct();
    if(id === "coming"){
        document.getElementById("coming").style.display = "block";
        document.getElementById("expend").style.display = "none";

    }
    else if(id === "expend"){
        document.getElementById("coming").style.display = "none";
        document.getElementById("expend").style.display = "block";
    }
}

function buttonSwitch(id) {
    if(id === "coming"){
        document.getElementById("buttonComing").style.backgroundColor = "#186e99";
        document.getElementById("buttonExpend").style.backgroundColor = "#113c57";
    }
    else if(id === "expend"){
        document.getElementById("buttonComing").style.backgroundColor = "#113c57";
        document.getElementById("buttonExpend").style.backgroundColor = "#186E99";
    }
}
//
function swapForm(id,buttonAdd,buttonDelete,buttonCancel) {
    if(document.getElementById(id).style.display === "none"){
        document.getElementById(id).style.display = "block";
        document.getElementById(buttonAdd).style.display = "none";
        document.getElementById(buttonDelete).style.display = "none";
        document.getElementById(buttonCancel).style.display = "block";
    }
    else {
        document.getElementById(id).style.display = "none";
        document.getElementById(buttonAdd).style.display = "block";
        document.getElementById(buttonDelete).style.display = "block";
        document.getElementById(buttonCancel).style.display = "none";
    }
}


shHide = function (c) {
    var a;
    return function (b) {
        a != b ? (b.className = c, a && (a.className = ""), a = b) : a = b.className = "";
        if (a === b) {
            document.getElementById('id_delete').value = a.getAttribute('id');
            let elem =  document.getElementsByClassName("divCenterH");
            for(let i = 0;i<elem.length;i++){
                elem[i].style.display = "none";
            }
            document.getElementById(b.getAttribute('id')*-1).style.display = "block";

        } else {
            document.getElementById('id_delete').value = "";
            document.getElementById(b.getAttribute('id')*-1).style.display = "none"
        }
    }
}("tab_act");
shHide_s = function (c) {
    var a;
    return function (b) {
        a != b ? (b.className = c, a && (a.className = ""), a = b) : a = b.className = "";
        if (a === b) {
            document.getElementById('id_delete_sales').value = a.getAttribute('id');
            let elem =  document.getElementsByClassName("divCenterS");
            for(let i = 0;i<elem.length;i++){
                elem[i].style.display = "none";
            }
            document.getElementById(b.getAttribute('id')*-1).style.display = "block";

        } else {
            document.getElementById('id_delete_sales').value = "";
            document.getElementById(b.getAttribute('id')*-1).style.display = "none"
        }
    }
}("tab_act");

function validateDelete(id){
    if(document.getElementById(id).value === ''){
        alert("Выбирете строку для удаления");
        return false;
    }
}
function validateForm(form,date,counteragent,warehouse,jsonProducts){
    let valid = true;

    if(document.forms[form][date].value === ""){
        document.forms[form][date].style.borderColor = '#ff0800';
        valid = false;
    }
    if(document.forms[form][counteragent].value === ""){
        document.forms[form][counteragent].style.borderColor = '#ff0800';
        valid = false;
    }
    if(document.forms[form][warehouse].value === ""){
        document.forms[form][warehouse].style.borderColor = '#ff0800';
        valid = false;
    }
    if(products.nameProduct.length === 0){
        // document.getElementById("nameProduct").style.borderColor = '#ff0800';
        // document.getElementById("quantity").style.borderColor = '#ff0800';
        // document.getElementById("unit").style.borderColor = '#ff0800';
        // document.getElementById("price").style.borderColor = '#ff0800';
        alert("Добавте хотябы один товар");
        valid = false
    }
    else
        document.getElementById(jsonProducts).value = JSON.stringify(products);

    return valid;
}

function saveProduct(nameProduct,quantity,unit,price,productDiv,typeInvoice){
    let valid = true;
    if(document.getElementById(nameProduct).value === ''){
        document.getElementById(nameProduct).style.borderColor = '#ff0800';
        valid = false;
    }
    if(document.getElementById(quantity).value === ''){
        document.getElementById(quantity).style.borderColor = '#ff0800';
        valid = false;
    }
    else if(document.getElementById(quantity).value <= 0){
        document.getElementById(quantity).style.borderColor = '#ff0800';
        valid = false;
    }
    if(document.getElementById(unit).value === ''){
        document.getElementById(unit).style.borderColor = '#ff0800';
        valid = false;
    }
    if(document.getElementById(price).value === ''){
        document.getElementById(price).style.borderColor = '#ff0800';
        valid = false;
    }
    if(valid){
        let exist;
        for(let i =0; i < products.nameProduct.length;i++){
            if(products.nameProduct[i] === document.getElementById(nameProduct).value && products.price[i] === document.getElementById(price).value && products.unit[i] === document.getElementById(unit).value){
                products.quantity[i] = products.quantity[i] * 1 + document.getElementById(quantity).value * 1;
                exist = true;
            }
        }
        if(!exist){
            products.nameProduct.push(document.getElementById(nameProduct).value);
            products.quantity.push(document.getElementById(quantity).value);
            products.unit.push(document.getElementById(unit).value);
            products.price.push(document.getElementById(price).value);
            products.type = typeInvoice;
        }
        updateTable(productDiv);
    }
}

function updateTable(productDiv){
    let table =
        "<table>" +
        "<th>Наименование</th>" +
        "<th>Количество</th>" +
        "<th>Ед.измерения</th>" +
        "<th>Цена</th>" +
        "<th>Общая стоимость</th>";
    for (let i = 0;i<products.nameProduct.length;i++){
        table = table.concat("<tr><td>");
        table = table.concat(products.nameProduct[i]);
        table = table.concat("</td>");
        table = table.concat("<td>");
        table = table.concat(products.quantity[i]);
        table = table.concat("</td>");
        table = table.concat("<td>");
        table = table.concat(document.getElementById(products.unit[i]).textContent);
        table = table.concat("</td>");
        table = table.concat("<td>");
        table = table.concat(products.price[i]);
        table = table.concat("</td>");
        table = table.concat("<td>");
        table = table.concat(products.price[i] + products.quantity[i]);
        table = table.concat("</td></tr>");
    }
    table = table.concat("</table>");
    document.getElementById(productDiv).innerHTML = table;
}
function clearProduct(){
    products.nameProduct = [];
    products.quantity = [];
    products.unit = [];
    products.price = [];
    products.type = '';
    document.getElementById("productDiv").innerHTML = "";
    document.getElementById("productDiv_sales").innerHTML = "";
}

