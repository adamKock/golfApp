<template>
  <div style="max-width: 400px; margin: auto;">
    <canvas ref="chartCanvas" width="400" height="400"></canvas>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { Chart, registerables } from 'chart.js'

Chart.register(...registerables)

const props = defineProps({
  chartData: Object,
  chartOptions: Object,
  chartType: {
    type: String,
    default: 'pie'
  }
})

const chartCanvas = ref(null)
let chartInstance = null

const renderChart = () => {
  if (chartInstance) {
    chartInstance.destroy()
  }

  chartInstance = new Chart(chartCanvas.value, {
    type: props.chartType,
    data: props.chartData,
    options: props.chartOptions
  })
}

onMounted(() => {
  renderChart()
})

watch(() => props.chartData, renderChart, { deep: true })

</script>

<style scoped>
canvas {
  max-width: 1000%;
  max-height: 800px;
  display: block;
  margin: 0 auto;
}
</style>

