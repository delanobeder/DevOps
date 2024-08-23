<template>
  <layout-div>
    <h2 class="text-center mt-5 mb-3">Contato (Edição)</h2>
    <div class="card">
      <div class="card-header">
        <router-link class="btn btn-outline-info float-right" to="/contatos">Lista de Contatos
        </router-link>
      </div>
      <div class="card-body">
        <form>
          <div class="form-group">
            <label htmlFor="nome">Nome</label>
            <input v-model="contato.nome" type="text" required class="form-control" id="nome" name="nome" />
          </div>
          <div class="form-group">
            <label htmlFor="telefone">Telefone</label>
            <input type="tel" v-mask="'(##) #####-####'" v-model="contato.telefone" required class="form-control" id="telefone" name="telefone">
            <!--input v-model="contato.telefone" type="text" class="form-control" id="telefone" name="telefone" /-->
          </div>
          <div class="form-group">
            <label htmlFor="email">E-mail</label>
            <input v-model="contato.email" type="text" class="form-control" id="e-mail" name="e-mail" />
          </div>
          <div class="form-group">
            <label htmlFor="dataNasc">Data de Nascimento</label>
            <input type="tel" v-mask="'##/##/#####'" v-model="contato.dataNasc" class="form-control" id="dataNasc" name="dataNasc" />
          </div>
          <button @click="handleSave()" :disabled="isSaving" type="button" class="btn btn-outline-primary mt-3">
            Salva Contato
          </button>
        </form>
      </div>
    </div>
  </layout-div>
</template>
 
<script>
import axios from 'axios';
import LayoutDiv from '../LayoutDiv.vue';
import Swal from 'sweetalert2'

export default {
  name: 'ContatoEdit',
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
          title: 'Um Erro Ocorreu!',
          showConfirmButton: false,
          timer: 1500
        })
        return error
      })
  },
  methods: {
    handleSave() {
      this.isSaving = true
      const id = this.$route.params.id;
      axios.put(`/contatos/${id}`, this.contato)
        .then(response => {
          Swal.fire({
            icon: 'success',
            title: 'Contato atualizado com sucesso!',
            showConfirmButton: false,
            timer: 1500
          })
          this.isSaving = false
          this.$router.push('/contatos')
          return response
        })
        .catch(error => {
          this.isSaving = false
          Swal.fire({
            icon: 'error',
            title: 'Um Erro Ocorreu!',
            showConfirmButton: false,
            timer: 1500
          })
          return error
        });
    },
  },
};
</script>