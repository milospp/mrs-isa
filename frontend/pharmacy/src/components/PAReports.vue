<template>
 <div class="row">
      <div class="col-md-12">
        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-income">Income</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Medicines</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Appointmens</a></li>
          </ul>

            <div class="tab-content">

                 <div id="tab-income" class="tab-pane in fade active in">
                        <div class="row">
                        <div class="col-md-12">
                            <div>
                                <br>
                                <ul class="nav nav-tabs">
                                    <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-mesecni1">Monthly report</a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#kvartalni1">Quarterly report</a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#godisnji1">Annual report</a></li>
                                </ul>
                                
                                <div class="tab-content">
                                    <div id="tab-mesecni1" class="tab-pane in fade active in">
                                        <h1>Mesecni 1</h1>
                                    </div>
                                    
                                    <div id="kvartalni1" class="tab-pane fade">
                                        <h1>kvartalni 1</h1>
                                    </div>

                                    <div id="godisnji1" class="tab-pane fade">
                                        <h1>godisnji 1</h1>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                
                <div id="menu1" class="tab-pane fade">

                    <div class="row">
                        <div class="col-md-12">
                            <div>
                                <br>
                                <ul class="nav nav-tabs">
                                    <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-mesecni2">Monthly report</a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#kvartalni2">Quarterly report</a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#godisnji2">Annual report</a></li>
                                </ul>
                                
                                <div class="tab-content">
                                    <div id="tab-mesecni2" class="tab-pane in fade active in">
                                        <h1>Mesecni 2</h1>
                                    </div>
                                    
                                    <div id="kvartalni2" class="tab-pane fade">
                                        <h1>kvartalni 2</h1>
                                    </div>

                                    <div id="godisnji2" class="tab-pane fade">
                                        <h1>godisnji 2</h1>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div id="menu2" class="tab-pane fade">
                    
                    <div class="row">
                        <div class="col-md-12">
                            <div>
                                <br>
                                <ul class="nav nav-tabs">
                                    <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-mesecni3">Monthly report</a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#kvartalni3">Quarterly report</a></li>
                                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#godisnji3">Annual report</a></li>
                                </ul>
                                
                                <div class="tab-content">
                                    <div id="tab-mesecni3" class="tab-pane in fade active in">
                                        <h1>Mesecni 3</h1>
                                    </div>
                                    
                                    <div id="kvartalni3" class="tab-pane fade">
                                        <h1>kvartalni 3</h1>
                                    </div>

                                    <div id="godisnji3" class="tab-pane fade">
                                        <h1>godisnji 3</h1>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
      </div>
 </div>

</template>
<script>
import AuthService from '../service/AuthService.js';

export default {
    name: 'PAReports',
    data(){
        return {
            user: null,
            
        }
    },
    methods: {
       
    },
    created(){
        this.user = AuthService.getCurrentUser();
       
    },
}
</script>