import dom from "./dom.js";
// -------- DOM MANIPULATED --------------------
const createAcc = () => {
    dom.input.logEmail.style.display = 'inline';
    dom.input.logFullName.style.display = 'inline';
    dom.input.logRepeatPass.style.display = 'inline';
    dom.btn.registration.style.display = 'none';
    dom.btn.create.style.display = 'inline';
    dom.btn.back.style.display = 'inline';
    dom.btn.login.style.display = 'none';
};
const backAcc = () => {
    dom.input.logEmail.style.display = 'none';
    dom.input.logRepeatPass.style.display = 'none';
    dom.input.logFullName.style.display = 'none';
    dom.btn.registration.style.display = 'inline';
    dom.btn.create.style.display = 'none';
    dom.btn.back.style.display = 'none';
    dom.btn.login.style.display = 'inline';
};
//------------------------------------------------------
const getToken = () => localStorage.getItem("token");
const setToken = tk => localStorage.setItem("token",tk);
const getJSON = res => {
    if(!res.ok) { throw new Error("We can`t fetch the resource");}
    console.log('Inside get gson');
    return res.json();
};
const myError = res => console.log('Somes happens, mistake - '+res);
const notLogged = res => {
    dom.input.logName.value = "You are not logged, something went wrong!";
    dom.input.logName.style.color = 'red';
};
const parseMyProfile = data => {
    dom.output.myProfile.innerHTML = 
    `<h3>principal name - ${data.userName}<h3/>
    id = ${data.AppUser.id}<br>
    name from DB : ${data.AppUser.username}<br>
    mail :${data.AppUser.email}<br>
    full name : ${data.AppUser.fullName}<br>
    password (encrypted) : <br> ${data.AppUser.password}<br>
    users role ${data.AppUser.role}<br>
    createDate : ${data.AppUser.createDate}<br>`;
};
const loginView = data => {
        console.log('data = ');
        console.log(data);
        console.log("data.token = ");
        console.log(data.token);
        setToken(data.token);
        dom.block.anonimLog.style.display = 'none';
        dom.block.userLog.style.display = 'block';
        dom.output.helloUser.textContent = data.user.username + '!!!';
        console.log('data.user.username  '+data.user.username);    
};
function funcLogOut() {
        setToken(" ");
        dom.block.anonimLog.style.display = 'block';
        dom.block.userLog.style.display = 'none';
        dom.input.logPass.value = "";
        dom.input.logName.value = "";
        dom.output.myProfile.innerHTML = "";
        console.log('you logOuted');    
}
// ------------ LOGIN ---------------------
const loginAcc = () => {
    let url = 'http://localhost:8080/login';
    let dto = {userName: dom.input.logName.value, pass: dom.input.logPass.value};
    myPost(url, dto, loginView);
};
const getProfile = () => {
    let url = 'http://localhost:8080/profile';
    myGetAutorize(url, parseMyProfile);
};
const myPost = (url, dto, callback) => {
    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'}, 
        body: JSON.stringify(dto)})
    .then(getJSON)
    .then(callback)
    .catch(notLogged);
};
const myGet = (url, callback) => {
    fetch(url)
    .then(getJSON)
    .then(callback)
    .catch(myError);
};
const myGetAutorize = (url, callback) => {
    fetch(url, {
        method: 'GET',
        headers: {'Authorization': 'Bearer '+getToken()}})
    .then(getJSON)
    .then(callback)
    .catch(myError);
};
const funcRegister = () => {
    if(dom.input.logPass.value !== dom.input.logRepeatPass.value) {
        console.log('Pass not same as repeatPass');
    } else {
        let dto = {
            username: dom.input.logName.value,
            fullName: dom.input.logFullName.value,
            email: dom.input.logEmail.value,
            password: dom.input.logPass.value
        };
        console.log('We send register dto :');
        console.log(dto);
        myPost('http://localhost:8080/register', dto, backAcc);
    };
};
//---------------- ANONYMOUS --------------------------------------
//backAcc();  // use css
dom.btn.registration.addEventListener('click', createAcc);
dom.btn.create.addEventListener('click', funcRegister);
dom.btn.back.addEventListener('click', backAcc);
dom.btn.login.addEventListener('click', loginAcc);
// --------------- USER -------------------
dom.btn.getProfile.addEventListener('click', getProfile);
dom.btn.logOut.addEventListener('click', funcLogOut);