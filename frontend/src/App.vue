<template>
  <div id="app" class="min-h-screen bg-gray-100">
    <!-- Top Menu -->
    <nav class="bg-white border-gray-200 dark:bg-gray-900 dark:border-gray-700">
      <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
        <a href="#" class="flex items-center space-x-3 rtl:space-x-reverse">
          <img src="https://flowbite.com/docs/images/logo.svg" class="h-8" alt="Flowbite Logo" />
          <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">MathsWork</span>
        </a>
        <button
          data-collapse-toggle="navbar-multi-level"
          type="button"
          class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
          aria-controls="navbar-multi-level"
          aria-expanded="false"
        >
          <span class="sr-only">Open main menu</span>
          <svg
            class="w-5 h-5"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 17 14"
          >
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 1h15M1 7h15M1 13h15"/>
          </svg>
        </button>
      </div>
    </nav>

    <div id="container">
      <p>&nbsp;</p>

      <!-- Centered Search -->
      <div class="max-w-2xl mx-auto">
        <form class="flex items-center" @submit.prevent="handleSubmit">
          <label for="simple-search" class="sr-only">Enter a number to save</label>
          <div class="relative w-full">
            <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
              <svg
                class="w-5 h-5 text-gray-500 dark:text-gray-400"
                fill="currentColor"
                viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path>
              </svg>
            </div>
            <input
              type="number"
              v-model="integer"
              id="simple-search"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Search"
              required
            />
          </div>
          <button
            type="submit"
            class="p-2.5 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Add
          </button>
          <button
            type="button"
            
            @click="fetchMathsWorks"
            class="p-2.5 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Show
          </button>
        </form>
      </div>
    </div>
 
    <div id="content" class="flex items-center justify-center p-8">
      
      <table v-if="mathsWorks.length > 0"  class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
         
          <th scope="col" class="px-6 py-3">Data</th>
        </thead>
        <tbody>
          <tr v-for="mathsWork in mathsWorks" :key="mathsWork.id"  class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
            <td class="px-6 py-4">{{ mathsWork.data }}</td>
          </tr>
        </tbody>
      </table>
    
      
    </div>
    <div  class="flex items-center justify-center p-8">
      <div class="mt-4" v-if="mathsWorks.length > 0">
        
        <button
          @click="prevPage"
          :disabled="currentPage === 1 || mathsWorks.length === 0"
          class="px-4 py-2 mr-2 bg-gray-300"
          :class="{ 'cursor-not-allowed': currentPage === 1 || mathsWorks.length === 0 }"
        >
          Previous
        </button>
        <button
          @click="nextPage"
          :disabled="currentPage === totalPages"
          class="px-4 py-2 bg-gray-300"
          :class="{ 'cursor-not-allowed': currentPage === totalPages }"
        >
          Next
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios from 'axios';

export interface MathWorkResponse {
  totalItems: number;
  totalPages: number;
  mathwork: MathsWork[];
  currentPage: number;
}

export interface MathsWork {
  id?: number;
  data: number;
}

export default defineComponent({
  setup() {
    const integer = ref('');
    const mathsWorks = ref<MathsWork[]>([]);
    const currentPage = ref(1);
    const pageSize = 5;
    const totalPages = ref(1);

    const fetchMathsWorks = async () => {
  try {
    const response = await axios.get<MathWorkResponse>(`http://localhost:8080/api/integers/index?page=${currentPage.value}&size=${pageSize}`);
    mathsWorks.value = response.data.mathwork;
    totalPages.value = response.data.totalPages;
  } catch (error) {
    console.error('Error fetching MathsWorks data:', error);
  }
};


    const handleSubmit = async () => {
      if (!integer.value) {
        alert('Please enter a number before adding.');
        return;
      }

      try {
        const newMathsWork: MathsWork = { data: parseInt(integer.value) };
        await axios.post('http://localhost:8080/api/integers/create', newMathsWork);
        alert(`${integer.value} added successfully`);
       // fetchMathsWorks();
      } catch (error) {
        console.error('Error adding MathsWork:', error);
      }
    };

    const nextPage = async () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
    await fetchMathsWorks();
  }
};


    const prevPage = () => {
      if (currentPage.value > 1) {
        currentPage.value--;
        fetchMathsWorks();
      }
    };

    return {
      integer,
      mathsWorks,
      currentPage,
      totalPages,
      handleSubmit,
      fetchMathsWorks,
      nextPage,
      prevPage,
    };
  },
});
</script>