setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                current: [],
                archives: [],
                filteredAr: [],
                checked: [],
                searchInput: '',
                isLoading: false,
                searchInput: ''
            }
        },
        created() {
            this.getCurrent()
            this.getArchives()
        },
        methods: {
            logOut() {
                axios.post("/api/logout")
                    .then(res => {
                        if (res.status == 200) {
                            Swal.fire({
                                position: 'center',
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
            getArchives() {
                axios.get(`/api/archives/getArchives`)
                    .then(res => {
                        this.archives = res.data.sort((a, b) => b.id - a.id)
                    }).catch(err => console.log(err))
            },
            addArFav(id) {
                if (this.current.length != 0) {
                    axios.post(`/api/favorite/addArFav`, `id=${id}`)
                        .then(res => {
                            this.backMsg = res.data
                            Swal.fire({
                                position: 'center',
                                title: `${this.backMsg}`,
                                showConfirmButton: false,
                                timer: 700
                            })
                        }).catch(err => {
                            this.backMsg = err.response.data
                            Swal.fire({
                                position: 'center',
                                title: `${this.backMsg}`,
                                showConfirmButton: false,
                                timer: 1000
                            })
                        })
                } else {
                    Swal.fire({
                        position: 'center',
                        title: `Debes estar logueado`,
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            }
        },
        computed: {
            filtroArCheckSearch() {
                this.filteredAr = this.archives.filter(archive => archive.name.toLowerCase().includes(this.searchInput.toLowerCase()))
            }
        }
    }).mount("#app")
}, 1000)