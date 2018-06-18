<template>
<div>
  <loader v-if="loading" height="500"></loader>
  <div v-if="!loading" class="row">
  <!-- Cuando no se han encontrado cursos -->
  <div v-if="exams.length === 0" class="text-center col-sm-12">
    <div class="alert alert-info">
      <icon name="frown-o"></icon> No tiene examens que resolver
    </div>
  </div>
    <!-- Listado de examenes -->
    <div class="card-columns">
        <div v-for="c in exams" class="card o-hidden h-70">
            <a :href="'servletExamUser?action=getExam&id='+ c.id">
                <img class="card-img-top img-fluid w-100" :src="c.image" :alt="c.title" />
            </a>
            <div class="card-body">
                <h4 class="card-title mb-1">{{ truncate(c.title) }}</h4>
            </div>
            <p class="card-text small">
                {{ c.description }}
            </p>
            <a :href="'servletExamUser?action=getExam&id='+ c.id" class="btn btn-dark btn-block">Resolver</a>
        </div>
    </div>
  
  </div>
</div>
</template>

<script>
import loader from "./global.loader.vue";
import icon from "./global.icon.vue";

export default {
  components: {
    loader,
    icon
  },
  name: "exams",
  data() {
    return {
      loading: false,
      exams: []
    };
  },
  mounted() {
    var self = this;
    self.all();
  },
  methods: {
    all() {
      let self = this;
      self.loading = true;

      $.post(
        "servletExamUser",
        {
          action: "getExamenesAsignados"
        },
        function(r) {
          self.exams = r.exams;
          self.loading = false;
        },
        "json"
      );
    },
    truncate(value) {
      if (value.length > 40) return value.substr(0, 37) + " ..";
      else return value;
    }
  }
};
</script>