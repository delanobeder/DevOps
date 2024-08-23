<template>
  <layout-div>
    <div class="container">
      <h2 class="text-center mt-5 mb-3">Lista de Contatos</h2>
      <div class="card">
        <div class="card-header">
          <router-link to="/contatos/create" class="btn btn-outline-primary float-start">Criar Novo Contato
          </router-link>
        </div>
        <div class="card-body">

          <table class="table table-bordered">
            <thead>
              <tr>
                <th>Nome</th>
                <th>Telefone</th>
                <th width="300px">Ação</th>
              </tr>
            </thead>
            <tbody>

              <tr v-for="contato in contatos" :key="contato.id">
                <td>{{ contato.nome }}</td>
                <td>{{ contato.telefone }}</td>
                <td>
                  <router-link :to="`/contatos/show/${contato.id}`"
                    class="btn btn-outline-info mx-1">Detalhes</router-link>
                  <router-link :to="`/contatos/edit/${contato.id}`"
                    class="btn btn-outline-success mx-1">Edição</router-link>
                  <button @click="handleDelete(contato.id)" className="btn btn-outline-danger mx-1">
                    Remove
                  </button>
                </td>
              </tr>

            </tbody>
          </table>
        </div>
      </div>
    </div>
  </layout-div>
</template>
 
<script>
import axios from 'axios';
import LayoutDiv from '../LayoutDiv.vue';
import Swal from 'sweetalert2'

export default {
  name: 'ContatoList',
  components: {
    LayoutDiv,
  },
  data() {
    return {
      contatos: []
    };
  },
  created() {
    this.fetchContatosList();
  },
  methods: {
    fetchContatosList() {
      axios.get('/contatos')
        .then(response => {
          this.contatos = response.data;
          return response
        })
        .catch(error => {
          console.log(error);
          return error
        });
    },
    handleDelete(id) {
      Swal.fire({
        title: 'Tem certeza?',
        text: "Não é possível reverter isso!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, remova-o!'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(`/contatos/${id}`)
            .then(response => {
              Swal.fire({
                icon: 'success',
                title: 'Contato removido com sucesso!',
                showConfirmButton: false,
                timer: 1500
              })
              this.fetchContatosList();
              return response
            })
            .catch(error => {
              Swal.fire({
                icon: 'error',
                title: 'Um erro ocorreu!',
                showConfirmButton: false,
                timer: 1500
              })
              return error
            });
        }
      })
    }
  },
};
</script>