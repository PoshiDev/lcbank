Vue.createApp({
    data() {
        return {
            cliente: [],
            cuenta: [],
            transacciones: [],
            transaccionesOrdenadas: [],

        }
    },

    created() {
        const params = new Proxy(new URLSearchParams(window.location.search), {
            get: (searchParams, prop) => searchParams.get("id"),
        });
        let value = params.some_key;
        console.log(value)

        axios.get('http://localhost:8585/api/accounts/' + value)
            .then(datos => {
                this.cuenta = datos.data
                console.log(this.cuenta)
                this.transacciones = datos.data.transactions
                console.log(this.transacciones)
                this.transaccionesOrdenadas = this.ordenarTransactions()
                console.log(this.transaccionesOrdenadas)
                this.transaccionesOrdenadas = this.transaccionesOrdenadas.filter(transaccion => transaccion.disableTransactions == false)
            }),

            axios.get('http://localhost:8585/api/clients/current')
                .then(datos => {
                    this.cliente = datos.data
                    console.log(this.cliente)
                })


    },

    methods: {

        cerrarSesion() {
            axios.post('/api/logout')
                .then(response => window.location.href = "https://littlechickenbank.herokuapp.com/web/index.html")

        },
        ordenarTransactions() {
            let auxiliar = this.transacciones
            auxiliar.sort((m1, m2) => m2.id - m1.id)
            return auxiliar
        },
    fechaFormateada(fecha){
        let date = new Date (fecha)
        console.log(date)
        console.log(date.getDate())

        let year = date.getFullYear() - 2000
        console.log(year)

        let month = date.getMonth() + 1
            if (month < 10){
                month = "0" + month
            }
        
        let day = date.getDate()
        console.log(day)
        if (day < 10){
            day = "0" + day
        }

        let yearAndMonth = day + "/" + month + "/"+ year
        return yearAndMonth
    },

  

    },

    computed: {


    }


}).mount("#app")

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