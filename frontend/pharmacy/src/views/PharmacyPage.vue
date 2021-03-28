<template>
<div>
  <NavBar/>
  
  <div class="container pt-5">
    <PharmacyProfileInfo/>


    <div class="row">
      <div class="col-md-12">

        <div>
          <h2>TODO More info</h2>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Home</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Doctors</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Appoitments</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Map</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                  </tr>
                  <tr>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu1" class="tab-pane fade">
              <h3>Staff</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                  </tr>
                  <tr>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu2" class="tab-pane in fade">

              <h3>Appointments</h3>

              <div class="appointment card mb-4">
                <div class="card-header">
                  <h5>Free apointment at 08:00 (30min)</h5>
                </div>
                <div class="card-body text-left">
                  <div class="row">
                    <div class="col-sm-6 h4">
                      <table>
                        <tr><td>Pharmacist: </td><td class="pl-5"><strong>Dr.Mr.Phill</strong></td></tr>
                        <tr><td>Pharmacy: </td><td class="pl-5"><strong>Phara</strong></td></tr>
                        <tr><td>Price per hour: </td><td class="pl-5"><strong>$20.00 USD</strong></td></tr>
                      </table>
                    </div>
                    <div class="col-sm-6 h5">
                      <table>
                        <tr><td>Starting at: </td><td class="pl-5"><strong>20/5/2021 08:00</strong></td></tr>
                      </table>
                      <br/>
                      <button class="btn btn-block btn-primary">Book a appointment</button>
                    </div>
                  </div>
                </div>
              </div> 

            </div>

            <div id="menu3" class="tab-pane fade">
            </div>
          </div>
        </div>
        

        
      </div>
    </div>

  </div>
</div>
</template>

<style>
  .in {
    opacity: 1 !important;
  }
  .nav-item.active > .nav-link {
    color: #495057;
    background-color: #fff;
    border-color: #dee2e6 #dee2e6 #fff;
  }
</style>

<script>
import NavBar from '@/components/NavBar.vue'
import PharmacyProfileInfo from '../components/PharmacyProfileInfo.vue'

// @ is an alias to /src
export default {
  name: 'PharmacyPage',
  components: {
    NavBar,
    PharmacyProfileInfo,
  }
}
</script>
