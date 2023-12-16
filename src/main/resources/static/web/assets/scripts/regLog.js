setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                current: [],
                firstName: '',
                lastName: '',
                userName: '',
                mail: '',
                nacionality: '',
                password: '',
                passwordConfirm: '',
                isLoading: false,
                mailLog: '',
                passwordLog: '',
                backMsg: '',
                selectedOpt: ''
            }
        },
        created() {
            this.getCurrent()
        },
        methods: {
            logOut() {
                axios.post("/api/logout")
                    .then(res => {
                        if (res.status == 200) {
                            Swal.fire({
                                position: 'center',
                                // icon: 'success',
                                title: 'Bye bye!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            setTimeout(() => {
                                window.location.href = "/web/index.html";
                            }, 1800)
                        }
                        console.log(res)
                    }).catch(err => {
                        Swal.fire({
                            position: 'center',
                            title: 'Cant log out, try again!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
            },
            getCurrent() {
                axios.get(`/api/getCurrent`)
                    .then(res => {
                        this.current = res.data
                        console.log(this.current)
                    }).catch(err => console.log(err))
            },
            register() {
                if (this.firstName &&
                    this.lastName &&
                    this.userName &&
                    this.mail &&
                    this.nacionality &&
                    this.password) {
                    if (this.password === this.passwordConfirm) {
                        axios.post(`/api/register`, `firstName=${this.firstName}&lastName=${this.lastName}&userName=${this.userName}&mail=${this.mail}&nacionality=${this.nacionality}&password=${this.password}`,
                            { headers: { 'content-type': 'application/x-www-form-urlencoded' } }
                        ).then(res => {
                            Swal.fire({
                                position: 'center',
                                title: 'Registro completo, iniciando login automático...',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            setTimeout(() => {
                                axios.post("/api/login", `mail=${this.mail}&password=${this.password}`)
                                    .then(res => {
                                        Swal.fire({
                                            position: 'center',
                                            title: 'Login ok, ingresando...',
                                            showConfirmButton: false,
                                            timer: 1700
                                        })
                                    }).catch(err => {
                                        console.log(err)
                                    })
                                setTimeout(() => {
                                    location.href = `/web/assets/pages/myProfile.html`
                                }, 1800)
                            }, 1600)
                        }).catch(err => {
                            this.backMsg = err.response.data
                            Swal.fire({
                                position: 'center',
                                title: `${this.backMsg}`,
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
                    } else {
                        return Swal.fire({
                            position: 'center',
                            title: "Las contraseñas no son iguales",
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                } else {
                    return Swal.fire({
                        position: 'center',
                        title: "Completa todos los datos",
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            },
            login() {
                if (this.mailLog && this.passwordLog) {
                    axios.post("/api/login", `mail=${this.mailLog}&password=${this.passwordLog}`)
                        .then(res => {
                            Swal.fire({
                                position: 'center',
                                title: 'Ingresando...',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            setTimeout(() => {
                                location.href = `/web/index.html`
                            }, 1600)
                        }).catch(err => {
                            Swal.fire({
                                position: 'center',
                                // icon: 'error',
                                title: `Datos erróneos, intenta de nuevo`,
                                showConfirmButton: false,
                                timer: 1500
                            })
                            this.mailLog = '',
                                this.passwordLog = ''
                        })
                } else {
                    Swal.fire({
                        position: 'center',
                        // icon: 'error',
                        title: `Ingresa todos los datos`,
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            }
        },
        computed: {
        }
    }).mount("#app")
}, 1000)