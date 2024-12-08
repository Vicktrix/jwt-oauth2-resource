const dom = {
    btn : {
        back : document.querySelector('#btnBack'),
        create : document.querySelector('#btnCreate'),
        registration : document.querySelector('#btnRegistration'),
        login : document.querySelector('#btnLogin'),
        logOut : document.querySelector('#btnLogOut'),
        logAdmin : document.querySelector('#btnLogAdmin'),
        getProfile : document.querySelector('#btnGetProfile'),
        backToUser : document.querySelector('#btnBackToUser'),
        contAdmin : document.querySelector('#btnContAdmin')
    },
    input : {
        logName : document.querySelector('#logName'),
        logEmail : document.querySelector('#logEmail'),
        logFullName : document.querySelector('#logFullName'),
        logPass : document.querySelector('#logPass'),
        logRepeatPass : document.querySelector('#logRepeatPass')
    },
    output : {
        helloUser : document.querySelector('#helloUser'),
        myProfile : document.querySelector('#myProfile')
    }, 
    block : {
        anonimLog : document.querySelector('#anonimLog'),
        userLog : document.querySelector('#userLog')
    }
    //    ------- LOGIN SELECTORS --------------    
    //   --------- USER SELECTORS ---------------    
    //   --------- ADMIN SELECTORS ---------------
//    const adminLog = document.querySelector('#adminLog');
//    const userPass = document.querySelector('#userPass');
//    const adminPass = document.querySelector('#adminPass');

};
export default dom;