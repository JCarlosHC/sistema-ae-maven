<template>
<div>
  <loader v-if="loading" height="200"></loader>
  <!-- Lessons List -->
  <ul v-if="!loading" class="list-group">
    <!-- Listado de las lecciones creadas -->
    <li v-for="item in questions" class="list-group-item">
        <div class="input-group">
            <input type="text" class="form-control" :value="item.question" readonly>
            <span class="input-group-btn">
            <button @click="remove(item.id)" class="btn btn-danger" type="button" title="Delete">
                <i class="fa fa-trash"></i>
            </button>
            <button @click="get(item.id)" type="button" data-toggle="modal" data-target="#question-edit" class="btn btn-default" title="Edit">
                <i class="fa fa-edit"></i>
            </button>
            </span>
        </div>
    </li>

    <!-- Nuevas Preguntas -->
    <li class="list-group-item">
        <div v-if="newEntry.Error.length > 0" class="alert alert-danger">{{ newEntry.Error }}</div>
        <div class="input-group">
            <input v-model="newEntry.Question" type="text" class="form-control" placeholder="New Question">
            <span class="input-group-btn">
                <button @click="insert" class="btn btn-default" type="button" title="Register">
                    <i class="fa fa-plus"></i>
                </button>
            </span>
        </div>
    </li>
  </ul>

  <!-- Modal -->
  <div class="modal fade" id="question-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog modal-lg" role="document">
          <div class="modal-content">
              <div class="modal-header">
                  <h4 class="modal-title" id="myModalLabel">Question</h4>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              </div>
              <div class="modal-body">
                <div v-if="entry.Error.length > 0" class="alert alert-danger">{{ entry.Error }}</div>
                <loader v-if="loadingEdit" height="200"></loader>
                <div v-if="!loadingEdit">
                  <div class="form-group">
                      <label>Question <span class="text-danger">*</span></label>
                      <input v-model="entry.Question" type="text" name="Question" class="form-control" value="Question #1" />
                  </div>
                  <div class="form-group">
                      <label>Unidad de temario<small>[Opcional]</small></label>
                      <input v-model="entry.UnitTemary" type="text" name="Unidad de temario" class="form-control" />
                      <small>Enter the unit of temary</small>
                  </div>
                  <div style="min-height:300px;">
                      <label>Answers</label>
                      <answerquestion :answers="entry.Answers" :questionId="entry.Id"></answerquestion>
                  </div>
                  <div class="text-right">
                      <button @click="update" type="button" class="btn btn-default">
                          Save
                      </button>
                  </div>
                </div>
              </div>
          </div>
      </div>
  </div>
</div>
</template>

<script>
import loader from "./global.loader.vue";
import answerquestion from "./teacher.answer.vue";

export default {
  name: "teacherquestion",
  components: {
    loader,
    answerquestion
  },
  props: {
    id: {
      type: Number,
      requide: true
    }
  },
  data() {
    return {
      loading: false,
      loadingEdit: false,
      newEntry: {
        Question: "",
        Error: ""
      },
      entry: {
        Id: 0,
        Question: "",
        CreatDate: "",
        UnitTemary: "",
        DischargeDate: "",
        IdStatus: 0,
        IdUser: 0,
        Answers: [],
        Error: ""
      },
      questions: []
    };
  },
  mounted() {
    this.all();
  },
  updated() {
    // Desde aqui podemos ejecutar plugins de jQuery
    var self = this;
    /*
    $("#wysiwyg").trumbowyg();

    // Pequeño hack para setear el valor manualmente
    $("#wysiwyg").on("tbwblur", function() {
      self.entry.Content = $(this).val();
    });*/
  },
  computed: {},
  methods: {
    all() {
      let self = this;
      self.loading = true;

      $.post(
        "servletQuestion",
        {
          examId: self.id,
          action: "getQuestions"
        },
        function(r) {
          self.questions = r.questions;
          self.loading = false;
        },
        "json"
      );
    },
    get(id) {
      var self = this;
      self.loadingEdit = true;

      $.post(
        "servletQuestion",
        {
          questionId: id,
          action: "getQuestion"
        },
        function(r) {
          self.entry.Id = r.id;
          self.entry.Question = r.question;
          self.entry.CreatDate = r.creatDate;
          self.entry.UnitTemary = r.unitTemary;
          self.entry.DischargeDate = r.dischargeDate;
          self.entry.IdStatus = r.idStatus;
          self.entry.IdUser = r.idUser;
          self.entry.Answers = r.answer;
          self.entry.Error = "";

          self.loadingEdit = false;
        },
        "json"
      );
    },
    update() {
      let self = this;
      self.loadingEdit = true;

      $.post(
        "servletQuestion",
        {
            examId: self.id,
            questionId: self.entry.Id,
            question: self.entry.Question,
            unitTemary: self.entry.UnitTemary,
            action: "saveQuestion"
        },
        function(r) {
          self.loadingEdit = false;

          if (r.msg != null) {
            // Si hay error mostramos mensaje
            self.newEntry.Error = r.msg;
          } else {
            self.newEntry.Error = "";
            self.all();
          }
        },
        "json"
      );
    },
    insert() {
      let self = this;
      self.loading = true;

      $.post(
        "servletQuestion",
        {
          examId: self.id,
          questionId: 0,
          question: self.newEntry.Question,
          action: "saveQuestion"
        },
        function(r) {
          self.loading = false;

          if (r.msg != null) {
            // Si hay error mostramos mensaje
            self.newEntry.Error = r.msg;
          } else {
            // En caso de éxito limpiamos todo
            self.newEntry.Question = "";
            self.newEntry.Error = "";

            self.all();
          }
        },
        "json"
      );
    },
    remove(id) {
      if (!confirm("Are you sure you are doing this action?")) {
        return;
      }

      let self = this;
      self.loading = true;

      $.post(
        "servletQuestion",
        {
          questionId: id,
          action: "deleteQuestion"
        },
        function(r) {
          self.loading = false;
          self.all();
        },
        "json"
      );
    }
  }
};
</script>