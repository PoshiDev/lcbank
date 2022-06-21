Vue.createApp({
    data() {
        return {
            cardType: "",
            colorType: "",
            maxCards: false,
            cards: [],
            creditCard: [],
            debitCard: [],
        }
    },

    created() {
        axios.get('http://localhost:8585/api/clients/current')
            .then(datos => {

                this.cards = datos.data.cards
                console.log(this.cards)

                this.creditCard = this.cards.filter(tarjeta => tarjeta.cardType == "CREDIT" && tarjeta.disable == false)
                console.log(this.creditCard)

                this.debitCard = this.cards.filter(tarjeta => tarjeta.cardType == "DEBIT" && tarjeta.disable == false)
                console.log(this.debitCard)
            })
    },


    methods: {
        cerrarSesion() {
            axios.post('/api/logout')
                .then(response => window.location.href = "https://lcbank.herokuapp.com/web/index.html")

        },

        nuevaTarjeta() {
            console.log(this.colorType)
            console.log(this.cardType)
            axios.post('http://localhost:8585/api/clients/current/cards', `cardType=${this.cardType}&colorType=${this.colorType}`,
                {
                    headers:
                        { 'content-type': 'application/x-www-form-urlencoded' }
                })
                .then(response => {
                    console.log("created");
                    location.reload()

                })
                .catch(error => {
                    console.log(error)
                    alertify.set('notifier', 'position', 'bottom-left');
                    alertify.error(error.request.response.includes("debit") ? "Max amount of DEBIT card reached" :
                        error.request.response.includes("credit") ? "Max amount of CREDIT card reached" : "Upss something went wrong")
                })
        },

        modalConfirmacion() {

            Swal.fire({
                title: 'Are you sure?',
                text: "Confirm to apply for a new card",
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire(
                        'Confirm!',
                        'You have a new card.',
                        'success'
                    ).then(() =>  this.nuevaTarjeta())
                }
            })



        },
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