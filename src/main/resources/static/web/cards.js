Vue.createApp({

    data() {
        return {
            cliente: [],
            cards: [],
            creditCard: [],
            debitCard: [],
            desabilitada: true,
            cardId: 0,

            expired: false,

        }
    },

    created() {
        axios.get('http://localhost:8585/api/clients/current')
            .then(datos => {
                this.cliente = datos.data
                console.log(this.cliente)

                this.cards = datos.data.cards
                console.log(this.cards)

                this.creditCard = this.cards.filter(tarjeta => tarjeta.cardType == "CREDIT" && tarjeta.disable == false)
                console.log(this.creditCard)

                this.debitCard = this.cards.filter(tarjeta => tarjeta.cardType == "DEBIT" && tarjeta.disable == false)
                console.log(this.debitCard)

                /* console.log(this.debitCard[0].thruDate)
    
                console.log(this.creditCard[0].number) */

            })
    },

    methods: {

        redirigir(){
            window.location.href = "https://lcbank.herokuapp.com/web/created-cards.html"
        },

        cerrarSesion() {
            axios.post('/api/logout')
                .then(response => window.location.href = "https://littlechickenbank.herokuapp.com/web/index.html")

        },

        fechaFormateada(fecha) {
            let date = new Date(fecha)

            let year = date.getFullYear() - 2000
            console.log(year)
            /* let newYear = Array.from(year.toString()).slice(-2).join("") */
            /*forma santi: year = year.getFullYear().toString().slice(-2) */
            /* datazo daphne: month = month.padStart(2, "0") con pad start le digo que tan largo quiero que sea, y con que se va a rellenar*/
            let month = date.getMonth() + 1
            if (month < 10) {
                month = "0" + month
            }
            let yearAndMonth = year + "/" + month
            return yearAndMonth
        },

        fechaFormateadaVencimiento(fecha) {
            let date = new Date(fecha)
            console.log(date)
            let today = new Date
            console.log(today)
            console.log(date > today)
            if (date > today) {
                this.expired = false
            } else {
                this.expired = true
            }



            let year = date.getFullYear() - 2000
            console.log(year)
            /* let newYear = Array.from(year.toString()).slice(-2).join("") */
            /*forma santi: year = year.getFullYear().toString().slice(-2) */
            /* datazo daphne: month = month.padStart(2, "0") con pad start le digo que tan largo quiero que sea, y con que se va a rellenar*/
            let month = date.getMonth() + 1
            if (month < 10) {
                month = "0" + month
            }
            let yearAndMonth = year + "/" + month
            return yearAndMonth
        },


        desabilitarTarjeta(id) {
            axios.patch('http://localhost:8585/api/clients/current/cards', `id=${id}`,
                {
                    headers:
                        { 'content-type': 'application/x-www-form-urlencoded' }
                }
            )
                .then(dato => setTimeout(location.reload(),3000)    /* console.log("desabilitada pa") */)
                .catch(error => console.log("nope"))
        },


        modalConfirmacion(id) {
            Swal.fire({
                title: 'Are you sure?',
                text: "Confirm to disable your card!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {

                    Swal.fire(
                        'Disabled it!',
                        'Your card has been disabled.',
                        'success'
                    ).then(() => this.desabilitarTarjeta(id))
                }
            })
        }






        /* numerosTarjetas(numeros){
            console.log(numeros)
            let numeros1 = numeros.toString().slice(0,4);
            console.log(numeros1)
            let numeros2 = numeros.toString().slice(4,8)
            console.log(numeros2)
            let numeros3 = numeros.toString().slice(8,12)
            console.log(numeros3)
            let numeros4 = numeros.toString().slice(12,16)
            console.log(numeros4)
            let numerosFormateados = numeros1 + " " + numeros2 + " " + numeros3 + " " + numeros4
            console.log(numerosFormateados)
            return numerosFormateados
        } */
    },

    computed: {

    },

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