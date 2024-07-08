

function handleLogin() {
    //  lấy thông tin từ in put
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    fetch("http://localhost:8080/api.com/v1/auth/sign-in", {
        method: "post",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },

        //make sure to serialize your JSON body
        body: JSON.stringify({
            username,
            password
        })
    })
        .then(res => res.json())
        .then((res) => {
            if (res.code == 200) {
                // lưu vào session 
                sessionStorage.setItem("token", res.data.accessToken)
                sessionStorage.setItem("name", res.data.fullName)

                if (res.data.authorities.some((auth) => auth.authority == "ROLE_USER")) {
                    location.href = "./"
                }
            }
            if(res.code==400){
                document.getElementById("error").innerHTML = res.messagge
            }
        })
        .catch(err => console.log(err))
}