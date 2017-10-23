<template>
<div class="center">
  <h2>{{title}}</h2>
    <img class="logo-big" src="./img/exitfactor_forwho01_gamers.png" />
    <p>
      Entrega Sprint 1
    </p>
</div>
</template>
<script>
export default{
  data(){
    return{
      title:'GameTweet'
    }
  },
  mounted:function(){
    console.log('Index.vue')
  }
}
</script>
