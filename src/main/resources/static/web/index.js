Vue.createApp({

    data() {
        return {
            mailUser: "",
            passwordUser: "",

            mensajeIngreso: false,
            pollitoEnojado: false,

            firstName: "",
            lastName: "",
            mail: "",
            password: "",

            welcomeBack: true,
            welcome: false,
        }
    },

    created() {
        axios.get('http://localhost:8585/api/clients/current',
            {
                headers: { 'accept': 'application/xml' }
            })
            .then(response =>

                console.log(response.data))


    },

    methods: {
        inicioSesion() {
            axios.post('/api/login', `email=${this.mailUser}&password=${this.passwordUser}`, {
                headers:
                    { 'content-type': 'application/x-www-form-urlencoded' }
            })
                .then(response => window.location.href = "https://littlechickenbank.herokuapp.com/web/accounts.html")
                .catch(message => {
                    this.pollitoEnojado = true;
                    alertify.set('notifier', 'position', 'bottom-left');
                    alertify.error("Your email or password is wrong")


                    /* this.mensajeIngreso = true */
                })
        },
        nuevoCliente() {
            axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&mail=${this.mail}&password=${this.password}`,
                {
                    headers:
                        /* {'accept':'application/xml'} */
                        { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(response => {
                    axios.post('/api/login', `email=${this.mail}&password=${this.password}`, {
                        headers:
                            { 'content-type': 'application/x-www-form-urlencoded' }
                    })
                        .then(response => window.location.href = "https://littlechickenbank.herokuapp.com/web/accounts.html")
                })
                .catch(error => {
                    console.log(error.request.response)

                    this.pollitoEnojado = true;
                    /* alertify.error('Something went wrong').moveTo(0,0); */
                    alertify.set('notifier', 'position', 'bottom-left');
                    alertify.error(error.request.response == "Missing data" ? "Complete the missing items" :
                        error.request.response == "Name already in use" ? "Email already in use" : "Upss something went wrong");

                })
        },
        mensajeNavWelcome(){
            this.welcomeBack = false;
            this.welcome = true;
        },
        mensajeNavBack(){
            this.welcomeBack = true;
            this.welcome = false;
        }

    },

    computed: {
        

    },

}).mount("#app")

$(function () {
    $(".btn").click(function () {
        $(".form-signin").toggleClass("form-signin-left");
        $(".welcomeBack").toggleClass(".welcome");
        $(".form-signup").toggleClass("form-signup-left");
        $(".frame").toggleClass("frame-long");
        $(".signup-inactive").toggleClass("signup-active");
        $(".signin-active").toggleClass("signin-inactive");
        $(".forgot").toggleClass("forgot-left");
        $(this).removeClass("idle").addClass("active");
    });
});


const burgerMenu = document.getElementById("burger");
const navbarMenu = document.getElementById("menu");

// Show and Hide Navbar Menu
burgerMenu.addEventListener("click", () => {
    burgerMenu.classList.toggle("is-active");
    navbarMenu.classList.toggle("is-active");

    if (navbarMenu.classList.contains("is-active")) {
        navbarMenu.style.maxHeight = navbarMenu.scrollHeight + "px";
    } else {
        navbarMenu.removeAttribute("style");
    }
});


