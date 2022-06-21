Vue.createApp({

    data() {
        return {
            loans:[],
            hipotecarios:[],
            personales:[],
            automotrices:[],

            hipotecario:[],
            personal:[],
            automotriz:[],

            client:[],
            cuentas:[],
            selectedLoan:[],
            numeroCuentaDestino:"VIN-XXXXXXXX",
            nombreLoan:"Loans",
            amount:0,
            pago:0,

            totalLoan:0,
            monthlyPayment:0,


        }
    },

    created() {
        axios.get('http://localhost:8585/api/loans')
        .then(datos => {
            this.loans = datos.data
            console.log(this.loans)

            this.hipotecarios = this.loans.filter(loan => loan.name == "Hipotecario")
            console.log(this.hipotecarios)

            this.hipotecario = this.hipotecarios[0]
            console.log(this.hipotecario)

            this.personales = this.loans.filter(loan => loan.name == "Personal")
            console.log(this.personal)

            this.personal = this.personales[0]
            console.log(this.personal)

            this.automotrices = this.loans.filter(loan => loan.name == "Automotriz")
            console.log(this.automotrices)

            this.automotriz = this.automotrices[0]
            console.log(this.automotriz)

            
        })
        .catch(error => console.log("hiciste algo mal gila"))
        axios.get('http://localhost:8585/api/clients/current')
        .then(datos => {

            this.client = datos.data
            console.log(this.client)

            this.cuentas = datos.data.accounts.sort((m1, m2) => m1.id - m2.id)
            console.log(this.cuentas)
            
        })
        .catch(error => console.log("hiciste algo mal gila x2"))

    },

    methods: {


        cerrarSesion() {
            axios.post('/api/logout')
                .then(response => window.location.href = "https://littlechickenbank.herokuapp.com/web/index.html")

        },
        
        selectNumberDestino(numeroCuenta){
            this.numeroCuentaDestino = numeroCuenta;
        },
        selectNombreLoan(nombre){
            this.nombreLoan = nombre;
        },
        selectPayments(cuotas){
            this.pago = cuotas;
        },
        newLoan(){
        axios.post('http://localhost:8585/api/loans',{loanName:this.nombreLoan,amount:this.amount,payment:this.pago,targetAccountNumber:this.numeroCuentaDestino},
        {headers:{'Content-Type': 'application/json'}}
        )
        .then(() => {
            console.log('client loan created');
            location.reload();
    })
        .catch(error => {
            console.log(error.response.data)
            alertify.set('notifier', 'position', 'bottom-left');
            alertify.error(error.request.response)

        })
    
        },
       
        

    },

    computed: {
        loanWithTaxes(){

            if(this.nombreLoan == 'Hipotecario'){
                let pct30 = (30 * this.amount) / 100
                this.totalLoan = this.amount + pct30
            }else if(this.nombreLoan == 'Personal'){
                let pct15 = (15 * this.amount) / 100
                this.totalLoan = this.amount + pct15
            }else{
                let pct20 = (20 * this.amount) / 100
                this.totalLoan = this.amount + pct20
            }
            
        },

        pagoPorMes(){
            this.monthlyPayment = Math.ceil(this.totalLoan / this.pago)
        },

    },



}).mount("#app")

/* --------------------- TERMINA VUE ------------------------ */

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

