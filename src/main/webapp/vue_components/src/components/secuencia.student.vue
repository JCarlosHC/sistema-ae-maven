<template>
    <div>
        <loader v-if="loading" height="200"></loader>
        <!-- Lessons List -->
        <ul v-if="!loading" class="list-inline">
            <!-- Listado de las lecciones creadas -->
            <li v-for="(item, index) in studentsList" :key="index" class="list-inline-item">
                <div class="input-group">
                    <input type="text" class="form-control" v-model="item.idstudent" readonly>
                    <span class="input-group-btn">
                        <button @click="remove(item.idstudent)" class="btn btn-danger" type="button" title="Delete">
                            <i class="fa fa-trash"></i>
                        </button>
                    </span>
                </div>
            </li>
             <!-- Nuevas Respuestas -->
            <li class="list-inline-item">
                <div v-if="newEntry.Error.length > 0" class="alert alert-danger">{{ newEntry.Error }}</div>
                <div class="input-group">
                    <input v-model="newEntry.Boleta" type="text" class="form-control" placeholder="New Student">
                    <span class="input-group-btn">
                        <button @click="insert" class="btn btn-default" type="button" title="Register">
                            <i class="fa fa-plus"></i>
                        </button>
                    </span>
                </div>
            </li>
        </ul>
    </div>
</template>
<script>
import loader from "./global.loader.vue";
export default {
  name: "secuenciastudent",
  components: {
    loader
  },
  props: {
    students: {
      type: Object,
      requide: true
    },
    idsecuencia: {
        type: String,
        requide: true
    }
  },
  data() {
    return {
      loading: false,
      loadingEdit: false,
      newEntry: {
        Boleta: "",
        Error: ""
      },
      studentsList: []
    };
  },
  mounted() {
    this.studentsList = this.students;
  },
  updated() {},
  methods: {
    insert() {
      let self = this;
      self.loading = true;

      $.post(
        "servletSecuencia",
        {
          id: self.idsecuencia,
          idstudent: self.newEntry.Boleta,
          action: "saveStudent"
        },
        function(r) {
          self.loading = false;

          if (r.msg != null) {
            // Si hay error mostramos mensaje
            self.newEntry.Error = r.msg;
          } else {
            // En caso de éxito limpiamos todo
            self.newEntry.Boleta = "";
            self.newEntry.Error = "";
            self.studentsList = r.studentsResp;
          }
        },
        "json"
      );
    },
    remove(idst) {
      if (!confirm("Are you sure you are doing this action?")) {
        return;
      }
      let self = this;
      self.loading = true;

      $.post(
        "servletSecuencia",
        { 
            id: self.idsecuencia, 
            idstudent: idst, 
            action: "deleteStudent" 
        },
        function(r) {
          self.loading = false;
          self.studentsList = r.studentsResp;
        },
        "json"
      );
    }
  }
};
</script>

