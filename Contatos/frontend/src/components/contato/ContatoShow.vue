<template>
  <layout-div>
    <h2 class="text-center mt-5 mb-3">Contato (Show)</h2>
    <div class="card">
      <div class="card-header">
        <router-link class="btn btn-outline-primary float-start" to="/contatos">Lista de Contatos
        </router-link>
      </div>
      <div class="card-body">
        <b className="text-muted">Nome:</b>
        <p>{{ contato.nome }}</p>
        <b className="text-muted">Telefone:</b>
        <p>{{ contato.telefone }}</p>
        <b className="text-muted">E-mail:</b>
        <p>{{ contato.email }}</p>
        <b className="text-muted">Data de Nascimento:</b>
        <p>{{ contato.dataNasc }}</p>
      </div>
    </div>
  </layout-div>
</template>
 
<script>
import axios from 'axios';
import LayoutDiv from '../LayoutDiv.vue';
import Swal from 'sweetalert2'

export default {
  name: 'ContatoShow',
  components: {
    LayoutDiv,
  },
  data() {
    return {
      contato: {
        nome: '',
        telefone: '',
        email: '',
        dataNasc: ''
      },
      isSaving: false,
    };
  },
  created() {
    const id = this.$route.params.id;
    axios.get(`/contatos/${id}`)
      .then(response => {
        let contatoInfo = response.data
        this.contato.nome = contatoInfo.nome
        this.contato.telefone = contatoInfo.telefone
        this.contato.email = contatoInfo.email
        this.contato.dataNasc = contatoInfo.dataNasc
        return response
      })
      .catch(error => {
        Swal.fire({
          icon: 'error',
          title: 'An Error Occured!',
          showConfirmButton: false,
          timer: 1500
        })
        return error
      })
  },
  methods: {

  },
};
</script>