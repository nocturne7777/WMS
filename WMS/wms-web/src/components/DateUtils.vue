<template>
  <div>
    <span class="time" id="time">
      时间 <span class="date">{{nowTime}}</span>
      <span class="hour" style="margin: 0 5px;">{{time.hour}}</span>
      <a class="split">:</a>
      <span class="minitus" style="margin: 0 5px;">{{time.minitus}}</span>
      <a class="split">:</a>
      <span class="seconds" style="margin: 0 5px;">{{time.seconds}}</span>
    </span>
  </div>
</template>

<script>
export default {
  name: 'DateUtils',
  data() {
    return {
      time: {
        hour: '',
        minitus: '',
        seconds: ''
      },
      nowTime: ''
    };
  },
  mounted() {
    this.updateTime();
    setInterval(() => {
      this.updateTime();
    }, 1000);
  },
  methods:{
    updateTime(){
      const date = new Date();
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始
      const day = String(date.getDate()).padStart(2, '0');
      const weekday = [
        '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'
      ][date.getDay()];
      this.nowTime = `${year}年${month}月${day}日 ${weekday}`;
      this.time.hour = String(date.getHours()).padStart(2, '0');
      this.time.minitus = String(date.getMinutes()).padStart(2, '0');
      this.time.seconds = String(date.getSeconds()).padStart(2, '0');
    }
  }
}

</script>



<style scoped>
  .time {
  font-size: 24px;
  color: #4e4949;
}
  .date {
  color: #917b73;
}

</style>