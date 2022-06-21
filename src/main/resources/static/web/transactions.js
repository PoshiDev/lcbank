Vue.createApp({

    data() {
        return {
            client: [],
            transacciones: [],
            cuentas: [],
            amount: 0,
            description: "",
            numeroCuentaOrigen: "VIN-XXXXXXXX",
            numeroCuentaDestino: "VIN-XXXXXXXX",
        }
    },
    created() {
        axios.get('http://localhost:8585/api/clients/current')
            .then(datos => {

                this.client = datos.data
                console.log(this.client)

                this.cuentas = datos.data.accounts.sort((m1, m2) => m1.id - m2.id)
                console.log(this.cuentas)

                this.transacciones = this.cuentas[0].transactions
                //.transactions.sort((m1, m2) => m2.id - m1.id)
                console.log(this.transacciones)
            })


    },

    methods: {
        cerrarSesion() {
            axios.post('/api/logout')
                .then(response => window.location.href = "https://littlechickenbank.herokuapp.com/web/index.html")

        },
        cancelado() {
            alertify.set('notifier', 'position', 'bottom-left');
            alertify.error('Transfer canceled');

        },

        nuevaTransaccion() {
            axios.post('http://localhost:8585/api/clients/current/transactions', `amount=${this.amount}&description=${this.description}&originAccountNumber=${this.numeroCuentaOrigen}&targetAccountNumber=${this.numeroCuentaDestino}`,
                {
                    headers:
                        { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(response => {
                    console.log("transaccion creada");
                    location.reload();
                    /* alertify.set('notifier', 'position', 'bottom-left');
                    alertify.success('Confirmed transfer'); */
                    
                })
                .catch(error => {
                    console.log(error.response.data)
                    alertify.set('notifier', 'position', 'bottom-left');
                    alertify.error(error.response.data)

                })
        },
        selectNumberOrigen(numeroCuenta) {
            this.numeroCuentaOrigen = numeroCuenta;
        },
        selectNumberDestino(numeroCuenta) {
            this.numeroCuentaDestino = numeroCuenta;
        },

        modalConfirmacion() {
            Swal.fire({
                title: 'Are you sure?',
                text: "Confirm the transfer",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Confirm!'
            }).then((result) => {
                if (result.isConfirmed) {
                    this.nuevaTransaccion();
                }
            })
        }




    },

    computed: {


    },

}).mount("#app")

$(function () {
    $(".btn1").click(function () {
        $(".form-signin").toggleClass("form-signin-left");
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

