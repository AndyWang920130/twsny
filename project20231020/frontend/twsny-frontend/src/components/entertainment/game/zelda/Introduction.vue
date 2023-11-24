<!--<template>-->
<!--  <a-card title="zelda 攻略">-->
<!--    <a-card-grid style="width: 25%; text-align: center" v-for="(item) in introductionItems" :key="item.id">-->
<!--      {{item.title}}-->
<!--    </a-card-grid>-->

<!--  </a-card>-->
<!--</template>-->

<!--<template>-->
<!--  <div style="background: #ececec; padding: 30px">-->
<!--    <a-card :title="item.title" :bordered="false" style="width: 300px" v-for="(item, index) in introductionItems">-->
<!--      <p>{{item.content}}</p>-->
<!--      <p>{{item.content}}</p>-->
<!--      <p>{{item.content}}</p>-->
<!--    </a-card>-->
<!--  </div>-->
<!--</template>-->

<template>
  <a-card hoverable style="width: 240px" v-for="(item, index) in introductionItems">
    <template #cover>
      <img alt="example" :src="item.url" />
    </template>
    <a-card-meta :title="item.title">
      <template #description>{{item.content}}</template>
    </a-card-meta>
  </a-card>
</template>


<script lang="ts" setup>
  import { ref } from 'vue';

  const introductionItems = ref([])
  introductionItems.value.push(
        {
          'id': "1",
          'url': "./src/images/20160615084017460.jpg",
          'title': "快速通关流程",
          'content': "出新手村（也就是初始台地），直接救公主。不推荐，那你就体会不到游戏探索的乐趣了，尤其是林克在大草原上奔跑的那种感觉，大家自己体会吧。而且初始台地出来没啥装备直接城堡里救公主，难度也不小，但是城堡里有很多好武器，也可以通关。"
        },
        {
          'id': '2',
          'url': "./src/images/58c9f01c943bf.jpg",
          'title': "最推荐流程",
          'content': "出新手村，边打四神兽边开神庙，过程中顺便拔出驱魔之剑，最后救公主。四神兽中，先打风兽或水兽都行，但是他俩一定要先打，因为打完之后给的技能是我最常用的两个，并且难度比较低。然后再打火兽，最后雷兽。这个流程是最推荐的流程，因为在这过程中，你就会到处乱跑去探索海利亚大陆，做一些小任务，收集和升级装备，触发一些隐藏和感人的剧情，这里就不剧透太多了。并且打完四神兽后，打最终boss时他的血量会减半"
        })
</script>