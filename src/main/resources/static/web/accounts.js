Vue.createApp({

    data() {
        return {
            cliente: [],
            cuentas: [],
            cuentasOrdenadas: [],
            cuenta1: [],
            cuenta2: [],
            loans: [],
            loan1: [],
            loan2: [],
            accountType: "",


        }
    },

    created() {

        axios.get('http://localhost:8585/api/clients/current')
            .then(datos => {
                this.cliente = datos.data
                console.log(this.cliente)

                this.cuentas = datos.data.accounts
                this.cuentas = this.cuentas.sort((m1, m2) => m1.id - m2.id)
                this.cuentas = this.cuentas.filter(cuenta => cuenta.disableAccount == false)
                console.log(this.cuentas)

                this.loans = datos.data.loans.sort((l1, l2) => l1.id - l2.id)
                console.log(this.loans)


            })
    },

    methods: {

        cerrarSesion() {
            axios.post('/api/logout')
                .then(response => window.location.href = "http://localhost:8585/web/index.html")

        },

        formateoFecha(fecha) {
            console.log(fecha)
            let date = new Date(fecha)

            let year = date.getFullYear()
            console.log(year)

            let month = date.getMonth()
            console.log(month)

            month = (month < 10) ? "0" + month : ""
            console.log(month)

            let day = date.getDay()
            console.log(day)

            day = (day < 10) ? "0" + day : ""
            console.log(day)

            let hour = date.getHours()
            console.log(hour)

            hour = (hour < 10) ? "0" + hour : ""

            let minutes = date.getMinutes()
            minutes = (minutes < 10) ? "0" + minutes : ""
            console.log(minutes)

            let horaCompleta = hour + ":" + minutes + "hs " + day + "/" + month + "/" + year
            console.log(horaCompleta)

            return horaCompleta

        },

        desabilitarCuenta(id) {
            axios.patch('http://localhost:8585/api/clients/current/accounts', `id=${id}`,
                {
                    headers:
                        { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(dato => /* console.log("sii") */ location.reload())
                .catch(error => console.log("nope"))
        },

        crearCuenta(color) {
            axios.post('http://localhost:8585/api/clients/current/accounts', `accountType=${color}`,
                {
                    headers:
                        { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(location.reload() /* console.log("sii") */)
                .catch(error => console.log(error))
        },

        modalConfirmacion() {
            (async () => {
                const inputOptions = new Promise((resolve) => {
                    setTimeout(() => {
                        resolve({
                            'SAVING': 'Saving Account',
                            'CURRENT': 'Current Account',
                        })
                    }, 1000)
                })
                const { value: color } = await Swal.fire({
                    title: "Please select your account's type",
                    input: 'radio',
                    inputOptions: inputOptions,
                    inputValidator: (value) => {
                        if (!value) {
                            return 'You need to choose something!'
                        }
                    }
                })
                if (color) {
                    Swal.fire({ html: `You selected: ${color}` })
                        .then(() => this.crearCuenta(color))
                };
                //Swal.fire('Saved!', '', 'success')
            })()
        },

        confirm(id) {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, disabled it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire(
                        'Disable it!',
                        'Your account has been disabled.',
                        'success'
                    ).then(() => this.desabilitarCuenta(id) )
                }
            })
        }

    },

    computed: {

    },

}).mount("#app")
/* 
document.addEventListener("DOMContentLoaded", function (event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        if (toggle && nav && bodypd && headerpd) {
            toggle.addEventListener('click', () => {
                nav.classList.toggle('show')
                toggle.classList.toggle('bx-x')
                bodypd.classList.toggle('body-pd')
                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header')

    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink() {
        if (linkColor) {
            linkColor.forEach(l => l.classList.remove('active'))
            this.classList.add('active')
        }
    }
    linkColor.forEach(l => l.addEventListener('click', colorLink))

}); */




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
