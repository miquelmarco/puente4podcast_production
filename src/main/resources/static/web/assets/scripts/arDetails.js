setTimeout(() => {
    let { createApp } = Vue;
    createApp({
        data() {
            return {
                current: [],
                archive: {},
                selectEp: [],
                comentariesAr: [],
                queryId: '',
                isLoading: false,
                //add Comment
                commentText: '',
                commentId: null,
                backMsg: '',
            }
        },
        created() {
            this.getCurrent()
            this.getComments()
        },
        methods: {
            addComment() {
                this.commentId = this.archive.id
                if (this.commentText && this.commentId) {
                    axios.post(`/api/archives/addArComment`, `comment=${this.commentText}&id=${this.commentId}`)
                        .then(res => {
                            this.getComments()
                            this.clearCommentTxt()
                        }).catch(err => {
                            Swal.fire({
                                position: 'center',
                                title: `Debes estar logueado para comentar`,
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
                } else {
                    Swal.fire({
                        position: 'center',
                        title: `No has agregado un comentario`,
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            },
            getComments() {
                this.queryId = new URLSearchParams(location.search).get('id')
                axios.get(`/api/archives/${this.queryId}`)
                    .then(res => {
                        this.archive = res.data
                        this.comentariesAr = this.archive.comentaryArDTOList.sort((a, b) => b.id - a.id)
                    }).catch(err => console.log(err))
            },
            enterCom: function () {
                this.commentId = this.archive.id
                if (this.current.length != 0) {
                    if (this.commentText && this.commentId) {
                        axios.post(`/api/archives/addArComment`, `comment=${this.commentText}&id=${this.commentId}`)
                            .then(res => {
                                this.getComments()
                                this.clearCommentTxt()
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
                        Swal.fire({
                            position: 'center',
                            title: `No has agregado un comentario`,
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                } else {
                    Swal.fire({
                        position: 'center',
                        title: `Debes estar logueado para comentar`,
                        showConfirmButton: false,
                        timer: 1500
                    })
                }
            },
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
            clearCommentTxt() {
                this.commentText = ''
            }
        },
        computed: {
        }
    }).mount("#app")
}, 1000)