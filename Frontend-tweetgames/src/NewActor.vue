<template>
  <div class="center">
    <h2>{{title}}</h2>

      <p>
        <label for="firstName">Nombre</label>
        <input type="text" id="firstName" name="firstName" required v-model="actor.firstName" onClick="this.select();" >
      </p>
      <p>
        <label for="lastName">Apellido</label>
        <input type="text" id="lastName" name="lastName" required v-model="actor.lastName" onClick="this.select();" >
      </p>
      <p><button v-on:click="addActor()"  >Agregar Actor</button></p>
      <p id="demo"></p>


  </div>
</template>

<script>
  export default {
    data(){
      return{
        title:'Agregar actor',
        actor:{firstName:'',lastName:''}
      }
    },
    methods:{
      addActor:function(){
        if (firstName.value == 0 && lastName.value == 0){
          alert("Tus campos no fueron llenados");
        }
        else if (firstName.value == 0){
          alert("No podemos ingresar a tu actor sin nombre");
        }
        else if (lastName.value == 0){
          alert("No podemos ingresar a tu actor sin apellido");
        }
        else{
            var txt;
              if (confirm("Deseas agregar a " + firstName.value + "?" ) == true) {
                txt = "usuario creado";
                this.$http.post('http://localhost:8090/TBDTaller1/actors', this.actor);
              }
              else {
                txt = "cancelaste la creacion de tu actor";
              }
              document.getElementById("demo").innerHTML = txt;
          }
        }
      }
    }
</script>
