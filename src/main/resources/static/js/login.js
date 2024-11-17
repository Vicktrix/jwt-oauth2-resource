
//    ------- LOGIN SELECTORS --------------
const anonimLog = document.querySelector('#anonimLog');
const logName = document.querySelector('#logName');
const logEmail = document.querySelector('#logEmail');
const logFullName = document.querySelector('#logFullName');
const logPass = document.querySelector('#logPass');
const logRepeatPass = document.querySelector('#logRepeatPass');
const btnBack = document.querySelector('#btnBack');
const btnCreate = document.querySelector('#btnCreate');
const btnRegistration = document.querySelector('#btnRegistration');
const btnLogin = document.querySelector('#btnLogin');

//   --------- USER SELECTORS ---------------
const userLog = document.querySelector('#userLog');
const helloUser = document.querySelector('#helloUser');
const btnLogOut = document.querySelector('#btnLogOut');
const btnLogAdmin = document.querySelector('#btnLogAdmin');
const btnGetProfile = document.querySelector('#btnGetProfile');
const myProfile = document.querySelector('#myProfile');

//   --------- ADMIN SELECTORS ---------------
const adminLog = document.querySelector('#adminLog');
const userPass = document.querySelector('#userPass');
const adminPass = document.querySelector('#adminPass');
const btnBackToUser = document.querySelector('#btnBackToUser');
const btnContAdmin = document.querySelector('#btnContAdmin');

// -------- DOM MANIPULATED --------------------
function createAcc(e) {
    logEmail.style.display = 'inline';
    logFullName.style.display = 'inline';
    logRepeatPass.style.display = 'inline';
    btnRegistration.style.display = 'none';
    btnCreate.style.display = 'inline';
    btnBack.style.display = 'inline';
    btnLogin.style.display = 'none';
    
}
function backAcc(e) {
    logEmail.style.display = 'none';
    logRepeatPass.style.display = 'none';
    logFullName.style.display = 'none';
    btnRegistration.style.display = 'inline';
    btnCreate.style.display = 'none';
    btnBack.style.display = 'none';
    btnLogin.style.display = 'inline';
}
//---------------- ANONYMOUS --------------------------------------
//backAcc();  // use css
btnRegistration.addEventListener('click', createAcc);
btnCreate.addEventListener('click', backAcc);
btnBack.addEventListener('click', backAcc);
btnLogin.addEventListener('click', loginAcc);
// --------------- USER -------------------
btnGetProfile.addEventListener('click', getProfile);

//------------------------------------------------------

function getToken() {
    return localStorage.getItem("token");
}
function setToken(tk) {
    localStorage.setItem("token",tk);
}
// ------------ LOGIN ---------------------
function loginAcc(e) {
    let url = 'http://localhost:8080/login';
    let dto = {userName: logName.value, pass: logPass.value};
//    myFetch(url, 'POST', {'Content-Type': 'application/json'}, dto,
            myPost(url, dto, 
        (data) => {
            setToken(data.token);
            anonimLog.style.display = 'none';
            userLog.style.display = 'block';
            helloUser.textContent = data.user.username + '!!!'
            console.log('data.user.username  '+data.user.username);
//            myProfile.textContent = ' My Token = '+data.token;
        });
}
function getProfile() {
    let url = 'http://localhost:8080/profile';
//    let header = {'Authorization': 'Bearer '+getToken()};
//    myFetch(url, 'GET', header, null, 
    myGetAutorize(url, 
    (data) => {
        myProfile.textContent = 
//                ' ttttt'+data.AppUser.email;
        `<h2>${data.userName}<h2/>
        ${data.AppUser}<br>
        ${data.AppUser.id}<br>
        ${data.AppUser.username}<br>
        ${data.AppUser.email}<br>
        ${data.AppUser.fullName}<br>
        ${data.AppUser.password}<br>
        ${data.AppUser.role}<br>
        ${data.AppUser.createDate}<br>`;
    })
};
// --------------- FETCH --------------------
//function myFetch(url, method, header, dto = null, callback) {
//    fetch(url, {
//        method: method,
//        headers: header, 
//        body: JSON.stringify(dto)})
//    .then(res => {
//        if(!res.ok) { throw new Error("We can`t fetch the resource");}
//        return res.json();
//    })
//    .then(data => {
//        callback(data);
//    })
//    .catch(error => console.error('Error - '+error));
//}
function myPost(url, dto, callback) {
    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'}, 
        body: JSON.stringify(dto)})
    .then(res => {
        if(!res.ok) { throw new Error("We can`t fetch the resource");}
        return res.json();})
    .then(data => {
        callback(data);})
    .catch(error => console.error('Error - '+error));
}
function myGet(url, callback) {
    fetch(url)
    .then(res => {
        if(!res.ok) { throw new Error("We can`t fetch the resource");}
        return res.json();})
    .then(data => {
        callback(data);})
    .catch(error => console.error('Error - '+error));
}
function myGetAutorize(url, callback) {
    fetch(url, {
        method: 'GET',
        headers: {'Authorization': 'Bearer '+getToken()}})
    .then(res => {
        if(!res.ok) { throw new Error("We can`t fetch the resource");}
        return res.json();})
    .then(data => {
        callback(data);})
    .catch(error => console.error('Error - '+error));
}
function getJSON(res){
    if(!res.ok) { throw new Error("We can`t fetch the resource");}
    return res.json();
}
function getTest(token) {
    let url = 'http://localhost:8080/profile';
    fetch(url, {
        method: 'GET',
        headers: {'Authorization': 'Bearer '+token}
    })
    .then(res => {
        if(!res.ok) { throw new Error("We can`t fetch the resource");}
        return res.json();})
    .then(data => {
            myProfile.textContent = 
    //                ' ttttt'+data.AppUser.email;
            `<h2>${data.userName}<h2/>
            ${data.AppUser}<br>
            ${data.AppUser.id}<br>
            ${data.AppUser.username}<br>
            ${data.AppUser.email}<br>
            ${data.AppUser.fullName}<br>
            ${data.AppUser.password}<br>
            ${data.AppUser.role}<br>
            ${data.AppUser.createDate}<br>`;
        })
    .catch(error => console.error('Error - '+error));
}