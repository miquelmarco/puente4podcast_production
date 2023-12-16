setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                //data
                current: [],
                modPassActual: '',
                modPass: '',
                modPassConfirm: '',
                selecModPass: false,
                //others
                isLoading: false,
                backMsg: ''
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
                    }).catch(err => console.log(err))
            },
            modiPass() {
                if (this.modPass && this.modPassConfirm && this.modPassActual) {
                    axios.patch(`/api/modPass`, `actualPass=${this.modPassActual}&newPass=${this.modPass}`)
                        .then(res => {
                            Swal.fire({
                                position: 'center',
                                title: 'Contraseña modificada',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            this.cleanData()
                        }).catch(err => {
                            console.log(err)
                            this.backMsg = err.response.data
                            Swal.fire({
                                position: 'center',
                                // icon: 'error',
                                title: `${this.backMsg}`,
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
                } else {
                    Swal.fire({
                        position: 'center',
                        title: 'Ingresa todos los datos',
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            },
            cleanData() {
                this.modPass = ""
                this.modPassConfirm = ""
                this.modPassActual = ""
            }
        },
        computed: {
        }
    }).mount("#app")
}, 1000)