<template>
    <table>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" data-toggle="modal" data-target="#napraviPregled" 
                      v-on:click.prevent="osveziDermatologe()" value="Make appointment"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
             <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make pricelist"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
           <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make action and promotion"></form> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="DodajFarmaceuta()">
                    <input type="submit" class="btn btn-primary" value="Add pharmacist"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            <td align="left"> <form v-on:submit.prevent="ZaposliDermatologa()">
                    <input type="submit" class="btn btn-primary" value="Hire a dermatologist"></form></td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            <td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#dodajLek">Add medicine</button></td>
        </tr>
        <tr> 
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Get report"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            <td align="left"><button type="button" class="btn btn-primary" data-toggle="modal" 
                data-target="#pharmacy">See pharmacy</button></td>
        </tr>
    </table> 
    <!-- Tabela sa podacima -->
    <div class="row">
      <div class="col-md-12">
        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Medicines</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Pharmacists</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Dermatologists</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Examination</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Orders</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu5">Invalid receipts</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu6">Map</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines</h3>
              <table class="table table-striped">
                 <thead class="card-header">
                  <th>Code</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Price</th>
                  <th>Quantity</th>
                  <th></th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.lekovi">
                      <td>{{l.medicine.code}}</td>
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.medicine.type}}</td>
                      <th>{{l.currentPrice}}</th>
                      <td>{{l.inStock}}</td>
                      <td><form v-on:click.prevent="funkcija(l, false)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
                      <td><form v-on:click.prevent="funkcija(l, true)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#brisanje">Delete</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu1" class="tab-pane fade">
              <h3>Pharmacists</h3>
              <table>
                <tr>
                  <td> &emsp; </td>
                  
                  <td align="left"><button type="button" class="btn btn-primary" data-toggle="modal" 
                    data-target="#filterFar">Filter</button></td>
                  <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  
                    <td> <input type="text" v-model="pharmaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretragaFarm()"> <input type="submit" data-toggle="modal" data-target="#obavestenje" value="Search"/></form></td>
                </tr>
                <tr>
                  <td> &emsp; </td>
                </tr>
              </table>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                  <th>Start time</th>
                  <th>End time</th>
                  <th>&emsp;</th>
                </thead>
                <tbody>
                    <tr :key="f.name" v-for="f in this.sviZaposleniFarmaceuti">
                      <td>{{f.name}}</td>
                      <td>{{f.surname}}</td>
                      <td>{{f.address["state"]}}, {{f.address["city"]}}, {{f.address["street"]}}, {{f.address["number"]}}</td>
                      <td>{{f.phoneNumber}}</td>
                      <td>{{(f.pharmacyWork.startHour[0] < 10 ? "0" + f.pharmacyWork.startHour[0] : f.pharmacyWork.startHour[0])}}:{{(f.pharmacyWork.startHour[1] < 10 ? "0" + f.pharmacyWork.startHour[1] : f.pharmacyWork.startHour[1])}}</td>
                      <td>{{(f.pharmacyWork.endHour[0] < 10 ? "0" + f.pharmacyWork.endHour[0] : f.pharmacyWork.endHour[0])}}:{{(f.pharmacyWork.endHour[1] < 10 ? "0" + f.pharmacyWork.endHour[1] : f.pharmacyWork.endHour[1])}}</td>
                      <td><form v-on:click.prevent="podesi(f, true)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#potvrda">Fire</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu2" class="tab-pane fade">
              <h3>Dermatologists</h3>
              <table>
                <tr>
                  <td> &emsp; </td>
                  <td align="left"><button type="button" class="btn btn-primary" data-toggle="modal" 
                    data-target="#filterDer">Filter</button></td>
                  <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  
                    <td> <input type="text" v-model="dermaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretragaDerm()"> <input type="submit" data-toggle="modal" data-target="#obavestenje" value="Search"/></form></td>
                </tr>
                <tr>
                  <td> &emsp; </td>
                </tr>
              </table>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                  <th>Start time</th>
                  <th>End time</th>
                  <th>&emsp;</th>
                </thead>
                <tbody>
                    <tr :key="d.name" v-for="d in this.sviZaposleniDermatolozi">
                      <td>{{d.name}}</td>
                      <td>{{d.surname}}</td>
                      <td>{{d.address["state"]}}, {{d.address["city"]}}, {{d.address["street"]}}, {{d.address["number"]}}</td>
                      <td>{{d.phoneNumber}}</td>
                      <td>{{(d.pharmacyWork.startHour[0] < 10 ? "0" + d.pharmacyWork.startHour[0] : d.pharmacyWork.startHour[0])}}:{{(d.pharmacyWork.startHour[1] < 10 ? "0" + d.pharmacyWork.startHour[1] : d.pharmacyWork.startHour[1])}}</td>
                      <td>{{(d.pharmacyWork.endHour[0] < 10 ? "0" + d.pharmacyWork.endHour[0] : d.pharmacyWork.endHour[0])}}:{{(d.pharmacyWork.endHour[1] < 10 ? "0" + d.pharmacyWork.endHour[1] : d.pharmacyWork.endHour[1])}}</td>
                      <td><form v-on:click.prevent="podesi(d, false)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#potvrda">Fire</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div id="menu3" class="tab-pane fade">
              <h3>Examination for dermatologists</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First ame</th>
                  <th>Last name</th>
                  <th>Phone number</th>
                  <th>Start time</th>
                  <th>Duration</th>
                  <th>Price</th>
                  <th>&emsp;</th>
                  <th>&emsp;</th>
                </thead>
                <tbody>
                    <tr :key="p.name" v-for="p in this.sviPreglediDermatologa">
                      <td>{{p.doctor.name}}</td>
                      <td>{{p.doctor.surname}}</td>
                      <td>{{p.doctor.phoneNumber}} </td>
                      <td> {{p.startTime}}</td>
                      <td>{{p.durationInMins}}</td>
                      <td>{{p.price}}</td>
                      <td><form v-on:click.prevent="podesiPregled(p)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#???">View</button></form></td>
                      <td><form v-on:click.prevent="podesiPregled(p)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#???">Delete</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div id="menu4" class="tab-pane fade">
              <h3>Orders</h3>
              <table>
                <tr>
                  <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                    <td> <select id="filterOrder" style="width: 100%;" v-on:change="filterNarudzbenica()" v-model="filterOrder" required="required">
                      <option>In process</option>
                      <option>Processed</option>
                    </select> </td>
                </tr>
                <tr>
                  <td> &emsp; </td>
                </tr>
              </table>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>Start date</th>
                  <th>End date</th>
                  <th>Number of items</th>
                  <th>Chosen offer</th>
                  <th>Number of offer</th>
                  <th>&emsp;</th>
                  <th>&emsp;</th>
                </thead>
                <tbody>
                    <tr :key="n.name" v-for="n in this.narudzbeniceZaIspis">
                      <td>{{n.startDate[1]}}/{{n.startDate[2]}}/{{n.startDate[0]}} {{n.startDate[3]}}:{{n.startDate[4]}}</td>
                      <td>{{n.endDate[1]}}/{{n.endDate[2]}}/{{n.endDate[0]}} {{n.startDate[3]}}:{{n.startDate[4]}}</td>
                      <td>{{n.allMedicines.length}}</td>
                      <td>{{n.chosenOffer?.supplier?.name}} {{n.chosenOffer?.supplier.surname}}</td>
                      <td>{{n.allOffer?.length}}</td>
                      <td><form v-on:click.prevent="izmeniNarudzbenicu(n)"><button type="button" class="btn btn-primary">View</button></form></td>
                      <td><div v-if="n.allOffer?.length == 0"><form v-on:click.prevent="postaviNarudzbenicu(n)">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obrisiNar">Delete</button>
                        </form></div></td>
                  </tr>
                </tbody>
              </table>
            </div>


            <div id="menu5" class="tab-pane fade">
              <h3>All invalid receipts</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Receipt</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Doctor 1</td>
                    <td>Receipt 1</td>
                  </tr>
                  <tr>
                    <td>Doctior 2</td>
                    <td>Receipt 2</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- mora na kraj jer inace ne radi kako valja -->
            <div id="menu6" class="tab-pane fade active">
					          <Mapa/>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Info o apoteci -->
  <div class="modal fade" id="pharmacy" tabindex="-1" role="dialog" aria-labelledby="Pharmacy info" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="PharmacyInfo">Pharmacy information</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Name: <input type="text" v-model="pharmacyName" placeholder=pharmacyName/></div>
        <div class="modal-body" align="left">Description: <input type="text" v-model="pharmacyDesc" placeholder=pharmacyDesc/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#odavestenje" data-dismiss="modal" v-on:click.prevent="proveraApoteka()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Info o leku -->
  <div class="modal fade" id="podaci" tabindex="-1" role="dialog" aria-labelledby="About medicine" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="AboutMedicine">About medicine</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Code: {{this.originalLeka?.medicine.code}}</div>
        <div class="modal-body" align="left">Name: <input type="text" v-model="name" placeholder=name/></div>
        <div class="modal-body" align="left">Structure: {{this.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: <input type="text" v-model="manufacturer" placeholder=manufacturer/></div>
        <div class="modal-body" align="left">Note: <input type="text" v-model="note" placeholder=note/></div>
        <div class="modal-body" align="left">Type: <input type="text" v-model="type" placeholder=type/></div>
        <div class="modal-body" align="left">Points: <input type="text" v-model="points" placeholder=points/></div>
        <div class="modal-body" align="left">Price: <input type="text" v-model="price" placeholder=price/></div>
        <div class="modal-body" align="left">Quantity: <input type="text" v-model="quantity" placeholder=quantity/></div>
        <div class="modal-body" align="left">With receipt (yes/no): <input type="text" v-model="perscription" placeholder=perscription/></div>
        <div class="modal-body" align="left">Shape: <input type="text" v-model="shape" placeholder=shape/></div>
        <div class="modal-body" align="left">Rating: {{this.rating}}</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary"  data-dismiss="modal" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="provera()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

      <!-- Filter dermatolog -->
  <div class="modal fade" id="filterDer" tabindex="-1" role="dialog" aria-labelledby="Filter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Filtercic">Infomation for search</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">First name: <input type="text" v-model="filterIme"/></div>
        <div class="modal-body" align="left">Last name: <input type="text" v-model="filterPrez"/></div>
        <div class="modal-body" align="left">Phone number: <input type="text" v-model="filterBroj"/></div>
        <div class="modal-body" align="left">State: <input type="text" v-model="filterAdrD"/></div>
        <div class="modal-body" align="left">City: <input type="text" v-model="filterAdrG"/></div>
        <div class="modal-body" align="left">Street: <input type="text" v-model="filterAdrU"/></div>
        <div class="modal-body" align="left">Number: <input type="text" v-model="filterAdrB"/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" data-dismiss="modal" v-on:click.prevent="filter(true)">Seaarch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

   <!-- Filter farmaceut -->
  <div class="modal fade" id="filterFar" tabindex="-1" role="dialog" aria-labelledby="Filter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Filtercic">Infomation for search</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">First name: <input type="text" v-model="filterIme"/></div>
        <div class="modal-body" align="left">Last name: <input type="text" v-model="filterPrez"/></div>
        <div class="modal-body" align="left">Phone number: <input type="text" v-model="filterBroj"/></div>
        <div class="modal-body" align="left">State: <input type="text" v-model="filterAdrD"/></div>
        <div class="modal-body" align="left">City: <input type="text" v-model="filterAdrG"/></div>
        <div class="modal-body" align="left">Street: <input type="text" v-model="filterAdrU"/></div>
        <div class="modal-body" align="left">Number: <input type="text" v-model="filterAdrB"/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="filter(false)">Seaarch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

   <!-- potvrda -->
  <div class="modal fade" id="potvrda" tabindex="-1" role="dialog" aria-labelledby="Potvrda" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Potvrdica">Are you sure you want to fire {{this.otpustiRadnika?.name}} {{this.otpustiRadnika?.surname}}?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="otpusti()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

     <!-- brisanje -->
  <div class="modal fade" id="brisanje" tabindex="-1" role="dialog" aria-labelledby="Brisanje" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Bris">Are you sure you want to delete medicine {{this.originalLeka?.medicine.name}}?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="izbrisiLek()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- obrisi narudzbenicu -->
  <div class="modal fade" id="obrisiNar" tabindex="-1" role="dialog" aria-labelledby="ObrisiNarudzbenicu" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="ObrisiNar">Are you sure you want to delete order?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="obrisiNarudzbenicu()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

    <!-- Dodaj leku -->
  <div class="modal fade" id="dodajLek" tabindex="-1" role="dialog" aria-labelledby="Dodaj lek" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="lekic">New medicine</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Code: <input type="text" v-model="code"/></div>
        <div class="modal-body" align="left">Name: <input type="text" v-model="name"/></div>
        <div class="modal-body" align="left">Structure: <input type="text" v-model="structure"/></div>
        <div class="modal-body" align="left">Manufacturer: <input type="text" v-model="manufacturer"/></div>
        <div class="modal-body" align="left">Note: <input type="text" v-model="note"/></div>
        <div class="modal-body" align="left">Type: <input type="text" v-model="type"/></div>
        <div class="modal-body" align="left">Points: <input type="text" v-model="points"/></div>
        <div class="modal-body" align="left">Price: <input type="text" v-model="price"/></div>
        <div class="modal-body" align="left">Quantity: <input type="text" v-model="quantity"/></div>
        <div class="modal-body" align="left">With receipt (yes/no): <input type="text" v-model="perscription"/> </div>
        <div class="modal-body" align="left">Shape: <input type="text" v-model="shape"/></div>
        <div class="modal-body" align="left">Replacement medicines: 
          <select id="zamenski" style="width: 50%;" v-model="zamenskiLekovi" required="required" multiple>
              <option v-for="m in this.sviLekovi" v-bind:value=m.id>{{m.name}}</option>
          </select></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal"  data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="dodajLek()">Save medicine</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

    <!-- obavestenje -->
  <div class="modal fade" id="obavestenje" tabindex="-1" role="dialog" aria-labelledby="poruka" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="poruka">Message</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left"><label>{{this.poruka}}</label></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="inicijalizujPoruku('Wait... Your require is in processing')">OK</button>
        </div>
      </div>
    </div>
  </div>

      <!-- Napravi pregled -->
  <div class="modal fade" id="napraviPregled" tabindex="-1" role="dialog" aria-labelledby="Napravi pregled" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="lekic">New appointment</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Start time: <input type="datetime-local" v-model="pregledStartuje"/></div>
        <div class="modal-body" align="left">Duration : <input type="number" min="1" v-model="pregledTraje"/> minutes</div>
        <div class="modal-body" align="left">Price: <input type="number" min="1" v-model="pregledKosta"/></div>
        <div class="modal-body" align="left">Chose dermatologist: 
          <select id="zamenski" style="width: 80%;" v-model="pregledDoktor" required="required">
              <option v-for="d in this.sviZaposleniDermatolozi" v-bind:value=d>{{d.name}} {{d.surname}} {{d.phoneNumber}}</option>
          </select></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal"  data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="dodajPregled()">Make</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import DermatologistDataService from '../service/DermatologistDataService.js';
import PharmacistDataService from '../service/PharmacistDataService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
import OrderDataService from '../service/OrderDataService.js';
import AppointmentDataService from '../service/AppointmentDataService.js';
import Mapa from "../components/Maps.vue";
import AuthService from "../service/AuthService.js";

export default {
    name: 'HpPharmacyAdmin',
    components: {
      Mapa
    },
    data() {
        return {
            sviZaposleniFarmaceuti : [],
            sviZaposleniDermatolozi : [],
            pharmacy: null,
            pharmacyName: null, pharmacyDesc: null,
            lekovi: [], originalLeka: null,
            name: null, structure: null, manufacturer: null, price: null, rating: null, code:null,
            note: null, points: null, type: null, quantity: null, shape: null, perscription: null,
            dermaSearch: "", pharmaSearch: "",
            filterIme: "", filterPrez: "", filterBroj: "",
            filterAdrD: "", filterAdrG: "", filterAdrU: "", filterAdrB: "", 
            otpustiRadnika: null, jesteFarmaceut: false, 
            sviLekovi: [], zamenskiLekovi: [], poruka: "Wait... Your require is in processing", 
            filterOrder: 0, narudzbenica: null, sveNarudzbenice: [], narudzbeniceZaIspis: [],
            pregledStartuje:null, pregledTraje: 0, pregledKosta: 0,  pregledDoktor: null,
            sviPreglediDermatologa: [], izabraniPregled: null, 
        };
    },
    created() {
      this.id = AuthService.getCurrentUser().id;
      PharmacyDataService.getPharmacyByIDAdmin(this.id)
        .then(response => {
          this.pharmacy = response.data;
          this.pharmacyName = this.pharmacy.name;
          this.pharmacyDesc = this.pharmacy.description;
          this.osveziPreglede();
        });
      this.osveziFarmaceute();
      this.osveziDermatologe();
      this.osveziLekove();
      MedicineDataService.getAllMedicines()
      .then(response => {
          this.sviLekovi = response.data;
      });
      this.osveziPorudzbine();

    },
    mounted() {
    },
    deleted() {
        localStorage.removeItem("narudzbenica");
    },
    methods : {
      osveziFarmaceute() {
        PharmacistDataService.getAllPharmacistAdmin(this.id)
          .then(response => { this.sviZaposleniFarmaceuti = response.data;});
      },
      osveziDermatologe() {
        DermatologistDataService.getAllDermatologistAdmin(this.id)
          .then(response => { this.sviZaposleniDermatolozi = response.data;});
      },
      osveziLekove() {
        MedicineDataService.getMedicineForPharmacyAdmin(this.id)
        .then(response => { this.lekovi = response.data;});        
      },
      osveziPorudzbine() {
        OrderDataService.getOrders(this.id)
        .then(response => {
          this.sveNarudzbenice = response.data;
          this.podesiDatume();          // da bude lepsi prikaz datuma
          this.narudzbeniceZaIspis = this.sveNarudzbenice;});   
      },
      osveziPreglede() {
        AppointmentDataService.getAppointmentApoteka(this.pharmacy.id)
        .then(response => {
          this.sviPreglediDermatologa = response.data;});   
      },

      inicijalizujPoruku(pk) { this.poruka = pk; },
      podesi(farm_der, jesteFar) { this.otpustiRadnika = farm_der; this.jesteFarmaceut = jesteFar;},
      postaviNarudzbenicu(p) { this.narudzbenica = p; },  // prelazak na drugu formu
      podesiPregled(p) { this.izabraniPregled = p; },

      otpusti() {
        if (this.jesteFarmaceut) {
          PharmacistDataService.firePharmacist(this.id, this.otpustiRadnika)        
          .then(response => {
            if (response.data == 0) { 
              this.poruka = "You successfully fired a pharmacist"; 
              this.osveziFarmaceute(); 
              return true;
            } 
            else if (response.data == -1) { 
              this.poruka = "Something goes wrong"; 
              return false;
            }
            else if (response.data == 2) { 
              this.poruka = "Refresh the page, you already fire this person"; 
              return false;
            }
            this.poruka = "Doctor has some review and you can't fire him or her!";
            return false;});
        }
        else {
        DermatologistDataService.fireDermatologist(this.id, this.otpustiRadnika)        
          .then(response => {
            if (response.data == 0) { 
              this.poruka = "You successfully fired a dermatologist"; 
              this.osveziDermatologe(); 
              return true;
            } 
            else if (response.data == -1) { 
              this.poruka = "Something goes wrong"; 
              return false;
            }
            else if (response.data == 2) { 
              this.poruka = "Refresh the page, you already fire this person"; 
              return false;
            }
            this.poruka = "Doctor has some review and you can't fire him or her!";
            return false;});
        }
      },
      filter(filtDermatologa) {
        var suma = this.filterIme.length + this.filterPrez.length + this.filterBroj 
          + this.filterAdrD.length + this.filterAdrG.length + this.filterAdrU.length + this.filterAdrB.length;
        if (suma == 0) {
          this.poruka = "You must enter some parameter for filter";
          return;
        }

        if (filtDermatologa) {            // za dermatologe
          DermatologistDataService.filterDermatologistAdmin(this.id, this.dermaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
            this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
            .then(response => {
              this.sviZaposleniDermatolozi = response.data;
              this.poruka = "Find " + this.sviZaposleniDermatolozi.length + " results";});
        }
        else {                            // za farmaceute
          PharmacistDataService.filterPharmacistAdmin(this.id, this.pharmaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
            this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
            .then(response => {
              this.sviZaposleniFarmaceuti = response.data;
              this.poruka = "Find " + this.sviZaposleniFarmaceuti.length + " results";});
        }

      },
      pretragaDerm() {
        if (this.dermaSearch.length == 0) {
          this.poruka = "Input someting for searching";
          return;
        }
        DermatologistDataService.searchDermatologistAdmin(this.id, this.dermaSearch)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;
          this.poruka = "Find " + this.sviZaposleniDermatolozi.length + " results";});
      },
      pretragaFarm() {
        if (this.pharmaSearch.length == 0) {
          this.poruka = "Input someting for searching";
          return;
        }
        PharmacistDataService.searchPharmacistAdmin(this.id, this.pharmaSearch)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;
          this.poruka = "Find " + this.sviZaposleniFarmaceuti.length + " results";});
      },
      DodajFarmaceuta() {
        window.location.href = "/addPharmacist/" + this.id;
        this.osveziFarmaceute();
      },
      ZaposliDermatologa() {
         window.location.href = "/hireDermatologist/" + this.id;
         this.osveziFarmaceute();
      },
      proveraApoteka() {
        if (this.pharmacyName.length == 0 || this.pharmacyDesc.length == 0) {
          this.poruka = "Name and description can't be empty.";
          return false;
        }
        this.pharmacy.name = this.pharmacyName;
        this.pharmacy.description = this.pharmacyDesc;
        PharmacyDataService.setPharmacy(this.pharmacy)
          .then(response => {this.poruka = "You successfaly changed pharmacy info.";});
      },
      funkcija(l, samoLek) {
        this.originalLeka = l;
        if (!samoLek) {
          this.name = l.medicine.name;
          if (l.medicine.perscription == "WITH_RECEIPT") this.perscription = "yes";  //novi
          else this.perscription = "no";  //novi
          this.structure =  l.medicine.structure;
          this.manufacturer = l.medicine.manufacturer;
          this.note = l.medicine.note;
          this.rating = l.medicine.rating;  // novo
          this.points = l.medicine.points;
          this.shape = l.medicine.shape;  // novo
          this.type = l.medicine.type;
          this.price = l.currentPrice;
          this.quantity = l.inStock;
        }
      },
      provera() {
        var provera = 0;
        provera += this.provera_prazan(this.name, "You must enter the name."); if (provera != 0) return false;
        provera += this.provera_prazan(this.manufacturer, "You must enter the manufacturer."); if (provera != 0) return false;
        provera += this.provera_prazan(this.note, "You must enter the note."); if (provera != 0) return false;
        provera += this.provera_prazan(this.type, "You must enter the type."); if (provera != 0) return false;
        provera += this.provera_prazan(String(this.quantity), "You must enter the quantity."); if (provera != 0) return false;
        provera += this.provera_prazan(String(this.price), "You must enter the price."); if (provera != 0) return false;
        provera += this.provera_prazan(this.shape, "You must enter the shape."); if (provera != 0) return false;
        provera += this.provera_prazan(this.perscription, "You must enter the perscription."); if (provera != 0) return false;
        provera += this.provera_prazan(this.points, "You must enter the points."); if (provera != 0) return false;
        provera += this.proveri_broj(String(this.quantity), "Quantity must be number."); if (provera != 0) return false;
        provera += this.proveri_broj(String(this.points), "Points must be number."); if (provera != 0) return false;
        provera += this.proveri_cenu(); if (provera != 0) return false;
        provera += this.provera_da_ne(); if (provera != 0) return false;

        this.originalLeka.medicine.name = this.name;
        this.originalLeka.medicine.manufacturer = this.manufacturer;
        this.originalLeka.medicine.note = this.note;
        this.originalLeka.medicine.type = this.type;
        this.originalLeka.inStock = this.quantity;
        this.originalLeka.currentPrice = this.price;
        this.originalLeka.medicine.points = this.points;
        this.originalLeka.medicine.shape = this.shape;
        this.originalLeka.medicine.perscription = this.perscription;
        MedicineDataService.editMedicinePharmacyAdmin(this.id, this.originalLeka)
          .then(response => {
              if (response.data == 1) { 
                this.poruka = "You successfully edited medicine";
                this.osveziLekove(); 
                return true;
              } 
              this.poruka = "Something goes wrong";
              return false;});
        return true;
      },
      proveri_broj(unos, poruka) {
        for (var karakter of unos) {
          if (karakter < '0' || karakter > '9') {
            this.poruka = poruka;
            return 1;
          }
        }
        return 0;
      },
      proveri_cenu() {
        var lista = String(this.price).split('.')
        if (lista.length > 2) {
          this.poruka = "Price must be in form 5.2";
          return 1;
        }
        for (var elem of lista) {
          var rez = this.proveri_broj(elem, "Price must be number in form 5.3") 
          if (rez == 1) return 1;
        }
        return 0;
      },
      provera_prazan(unos, poruka) {
        if (unos == null) {
          this.poruka = poruka;
          return 1;
        }
        if (unos.length == 0) {
          this.poruka = poruka;
          return 1;
        }
        return 0;
      },
      provera_da_ne() {
        if ( this.perscription == "WITH-RECEIPT" || this.perscription == "yes" || this.perscription == "YES" || this.perscription == "YeS" || this.perscription == "YEs" ||
          this.perscription == "yES" || this.perscription == "yeS" ||this.perscription == "Yes" || this.perscription == "yEs") {
          this.perscription = 0;
          return 0;
        } 
        if (this.perscription == "WITHOUT-RECEIPT" || this.perscription == "no" || this.perscription == "nO" || this.perscription == "No" || this.perscription == "NO") {
          this.perscription = 1;
          return 0;
        }
        this.poruka = "Perscription must be yes or on";
        return 1;
      },
      izbrisiLek() {
        MedicineDataService.deleteMedicinePharmacyAdmin(this.id, this.originalLeka.medicine.code)
          .then(response => {
              if (response.data == 1) { 
                this.poruka = "You successfully deleted medicine"; 
                this.osveziLekove();
                return true;
                } 
              if (response.data == 0) { this.poruka = "Refresh page, you already delete this medicine"; return false;} 
              this.poruka = "Something goes wrong";
              return false;});
        this.osveziLekove();
      },
      dodajLek() {
        var provera = 0;
        provera += this.provera_prazan(this.code, "You must enter the code."); if (provera != 0) return false;
        provera += this.provera_prazan(this.name, "You must enter the name."); if (provera != 0) return false;
        provera += this.provera_prazan(this.structure, "You must enter the structure."); if (provera != 0) return false;
        provera += this.provera_prazan(this.manufacturer, "You must enter the manufacturer."); if (provera != 0) return false;
        provera += this.provera_prazan(this.note, "You must enter the note."); if (provera != 0) return false;
        provera += this.provera_prazan(this.type, "You must enter the type."); if (provera != 0) return false;
        provera += this.provera_prazan(this.points, "You must enter the points."); if (provera != 0) return false;
        provera += this.provera_prazan(String(this.quantity), "You must enter the quantity."); if (provera != 0) return false;
        provera += this.provera_prazan(String(this.price), "You must enter the price."); if (provera != 0) return false;
        provera += this.provera_prazan(this.shape, "You must enter the shape."); if (provera != 0) return false;
        provera += this.provera_prazan(this.perscription, "You must enter the perscription."); if (provera != 0) return false;
        provera += this.proveri_broj(String(this.quantity), "Quantity must be number."); if (provera != 0) return false;
        provera += this.proveri_broj(String(this.points), "Quantity must be points."); if (provera != 0) return false;
        provera += this.proveri_cenu();
        provera += this.provera_da_ne(); if (provera != 0) return false;
        if (provera != 0) return false;

        var listaKodova = [];
        var brojac = 0;
        for (var ind of this.zamenskiLekovi) {
          listaKodova[brojac] =this.sviLekovi[ind - 1]?.code;
          brojac++;
        }

        var noviLek = {"medicine": {
          "code": this.code, "name": this.name, "structure": this.structure, "manufacturer": this.manufacturer,
          "note": this.note, "type": this.type, "shape": this.shape, "perscription": this.perscription,
          "replacementMedicationIds": listaKodova, "points": this.points}, 
          "currentPrice": this.price, "inStock": this.quantity};
        MedicineDataService.addMedicinePharmacyAdmin(this.id, noviLek)
          .then(response => {
              if (response.data == 1) { 
                this.poruka = "You successfully added medicine."; 
                this.osveziLekove();
                return true;
              } 
              if (response.data == 0) { 
                this.poruka = "Code is already used, enter the new code."; 
                return false;
              } 
              this.poruka = "Something goes wrong";
              return false;});
      },
      filterNarudzbenica() {
        this.narudzbeniceZaIspis = [];
        var brojac = 0;
        if (this.filterOrder == "In process")
          for (var pon of this.sveNarudzbenice) 
            if (pon.chosenOffer == null) {
              this.narudzbeniceZaIspis[brojac] = pon;
              brojac++;
            }
        else 
          for (var pon of this.sveNarudzbenice) 
            if (pon.chosenOffer != null) {
              this.narudzbeniceZaIspis[brojac] = pon;
              brojac++;
            }
      },
      obrisiNarudzbenicu() {
        OrderDataService.deleteOrder(this.narudzbenica)
        .then(response => {
          this.poruka = "You successfully deleted order.";
          this.osveziPorudzbine();
          });
      },
      izmeniNarudzbenicu(n) {
        // prvo za lekoveNarudzbenice
        var stringic = '[';
          for (var lek of n.allMedicines) {
            stringic += '{"medicine": "';
            stringic += lek.medicine.code;
            stringic += '", "quantity": "';
            stringic += lek.quantity;
            stringic += '"}'
            if (lek != n.allMedicines[n.allMedicines.length -1]) stringic += ", ";
          }
        stringic += "]";
        localStorage.setItem("lekoviNarudzbenice", stringic);

        // vrati datum da bude int[]
        n.endDate[1] = parseInt(n.endDate[1]);
        n.endDate[2] = parseInt(n.endDate[2]);
        n.endDate[3] = parseInt(n.endDate[3]);
        n.endDate[4] = parseInt(n.endDate[4]);
        n.startDate[1] = parseInt(n.startDate[1]);
        n.startDate[2] = parseInt(n.startDate[2]);
        n.startDate[3] = parseInt(n.startDate[3]);
        n.startDate[4] = parseInt(n.startDate[4]);
        
        var stringNarudzbenice = "{";
        stringNarudzbenice += '"startDate": [' + n.startDate + "], ";
        stringNarudzbenice += '"endDate": [' + n.endDate + "], ";
        stringNarudzbenice += '"author": {"name": "' + n.author.name + '", "surname": "' + n.author.surname + '"}';
        stringNarudzbenice += "}";
        localStorage.setItem("narudzbenica", stringNarudzbenice);
        window.location.href = "/changeOrder";
      },
      podesiDatume() {
        for (var por of this.sveNarudzbenice) {
          if (por.endDate[1] < 10) por.endDate[1] = "0" + por.endDate[1];
          if (por.endDate[2] < 10) por.endDate[2] = "0" + por.endDate[2];
          if (por.endDate[3] < 10) por.endDate[3] = "0" + por.endDate[3];
          if (por.endDate[4] < 10) por.endDate[4] = "0" + por.endDate[4];

          if (por.startDate[1] < 10) por.startDate[1] = "0" + por.startDate[1];
          if (por.startDate[2] < 10) por.startDate[2] = "0" + por.startDate[2];
          if (por.startDate[3] < 10) por.startDate[3] = "0" + por.startDate[3];
          if (por.startDate[4] < 10) por.startDate[4] = "0" + por.startDate[4]; 
        }
      },
      dodajPregled() {
        if (this.pregledStartuje == null) {
          this.poruka = "You must enter start time for appointment";
          return false;
        }
        if (this.pregledTraje <= 0) {
          this.poruka = "Appointment duration must be positive number";
          return false;
        }
        if (this.pregledKosta <= 0) {
          this.poruka = "Appointment price must be positive number";
          return false;
        }
        if (this.pregledDoktor == null) {
          this.poruka = "You must select one of dermatologist for appointment";
          return false;
        }

        AppointmentDataService.makeAppointmentPAdmin(this.pregledStartuje, this.pregledTraje, this.pregledKosta,
          this.pregledDoktor, this.pregledDoktor.pharmacyWork.pharmacyId)
          .then(response => {
            if (response.data == 0) this.poruka = "You successfully made appointment";
            else if (response.data == -1) this.poruka = "Dermatologist doesn't work in this pharmacy in inputed time";
            else if (response.data == -2) this.poruka = "Appointment is too long, end time is after end hour of dermatologist";
            else if (response.data == -3) this.poruka = "Dermatologist already have some appointment at inputed time";
            this.osveziPreglede();
            return;
          });
        },

    }
}
</script>
