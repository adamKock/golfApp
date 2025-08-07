<template>
  <div class="chart-container">
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { Chart, LineController, LinearScale, PointElement, LineElement, CategoryScale, Tooltip, Legend } from 'chart.js';

Chart.register(LineController, LinearScale, PointElement, LineElement, CategoryScale, Tooltip, Legend);

export default {
  props: {
    chartData: Object,
    chartOptions: Object
  },
  setup(props) {
    const chartCanvas = ref(null);
    let chartInstance = null;

    const renderChart = () => {
      if (chartInstance) {
        chartInstance.destroy();
      }
      
      if (chartCanvas.value) {
        chartInstance = new Chart(chartCanvas.value, {
          type: 'line',
          data: props.chartData,
          options: props.chartOptions
        });
      }
    };

    onMounted(renderChart);
    watch(() => props.chartData, renderChart, { deep: true });

    return {
      chartCanvas
    };
  }
};
</script>

<style scoped>
.chart-container {
  position: relative;
  height: 100%;
  width: 100%;
}
</style>