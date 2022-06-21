Vue.createApp({

    data() {
        return {
            jsonClientes: [],
            clientes: [],
            nombre: "",
            apellido: "",
            mail: "",
            url: "",
            index: 0,
            nombreTemporal: "",
            apellidoTemopral: "",
            mailTemporal: "",

        }
    },

    created() {
        axios.get('http://localhost:8585/rest/clients')
            .then(datos => {
                this.jsonClientes = datos.data
                this.clientes = datos.data._embedded.clients

            })

    },

    methods: {

        crearCliente() {

            axios.post('http://localhost:8585/rest/clients', {
                nombre: this.nombre,
                apellido: this.apellido,
                mail: this.mail,
                id: this.id

            })
                .then(function (loadData) {
                    location.reload(loadData);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

        eliminarCliente(id) {
            axios.delete(id)
                .then(function () {
                    location.reload()
                        .catch(error => console.log(error))
                })

        },

        abrirModal(cliente) {
            console.log(cliente)
            this.nombreTemporal = cliente.nombre
            this.apellidoTemporal = cliente.apellido
            this.mailTemporal = cliente.mail
            this.url = cliente._links.self.href

        },

        editarCliente() {

            axios.patch(this.url, {

                nombre: this.nombreTemporal,
                apellido: this.apellidoTemporal,
                mail: this.mailTemporal
            })
                .then(response => console.log(response))
                .catch(error => console.log(error))
        }
    },


    computed: {



    },

}).mount("#app")
