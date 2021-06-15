<template>
  <h3>{{this.pharmacy?.name}}</h3>
  <h5> Address: {{this.pharmacy?.address.state}}, {{this.pharmacy?.address.city}} ,
        {{this.pharmacy?.address.street}}, {{this.pharmacy?.address.number}} <br />
   Rating: {{this.pharmacy?.rating}} <br />
   Description: {{this.pharmacy?.description}} <br />
   Price per hour: {{this.pharmacy?.pricePerHour}}</h5>
    <table>
        <tr>
            <td align="left"> <form v-on:submit.prevent="DodajFarmaceuta()">
                    <input type="submit" class="btn btn-primary" value="Add pharmacist"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>

            <td align="left"> <form v-on:submit.prevent="ZaposliDermatologa()">
                    <input type="submit" class="btn btn-primary" value="Hire a dermatologist"></form></td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>

            <td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#dodajLek">Add medicine</button></td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" data-toggle="modal" data-target="#napraviPregled" 
                      v-on:click.prevent="osveziDermatologe()" value="Make examination"></form> </td>
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
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Map</a></li>
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
                  <th>Action/prom</th>
                  <th></th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.lekovi">
                      <td>{{l.medicine.code}}</td>
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.medicine.type}}</td>
                      <!-- prava cena -->
                      <td v-if="l.priceType=='NORMAL'">{{l.currentPrice}}</td>
                      <td v-else>{{l.oldPrice}}</td>

                      <td>{{l.inStock}}</td>
                      <!-- popust/akcija -->
                      <td v-if="l.priceType=='NORMAL'"></td>
                      <td v-else-if="l.priceType=='ACTION'">{{l.currentPrice}} ({{100-l.currentPrice*100/l.oldPrice}}%)</td>
                      <td v-else>{{l.currentPrice}}</td>
                      <!-- Nema promene dok je akcija/promocija i nema brisanja -->
                      <td v-if="l.priceType=='NORMAL'"><form v-on:click.prevent="funkcija(l, false)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
                      <td v-else> <form v-on:click.prevent="funkcija(l, false)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaciPromocija">View</button></form></td>
                      <td v-if="l.priceType=='NORMAL'"><form v-on:click.prevent="funkcija(l, true)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#brisanje">Delete</button></form></td>
                      <td v-else><form v-on:click.prevent="funkcija(l, true)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#brisanjeAkcije">Delete</button></form></td>
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
                  <th>Rating</th>
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
                      <td>{{f.rating}}</td>
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
                  <th>Rating</th>
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
                      <td>{{d.rating}}</td>
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
                      <td>{{p.startTime[2]}}/{{p.startTime[1]}}/{{p.startTime[0]}} {{p.startTime[3]}}:{{p.startTime[4]}}</td>
                      <td>{{p.durationInMins}}</td>
                      <td>{{p.price}}</td>
                      <td><form v-on:click.prevent="podesiPregled(p)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#izmeniPregled">View</button></form></td>
                      <td><form v-on:click.prevent="podesiPregled(p)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obrisiPregled">Delete</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- mora na kraj jer inace ne radi kako valja -->
            <div id="menu4" class="tab-pane fade active">
					          <Mapa/>
            </div>
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
        <div class="modal-body" align="left">With receipt: 
          <select id="potvrdaZahteva" style="width: 50%;" v-model="perscription" required="required">
              <option>Yes</option>
              <option>No</option>
          </select>
        </div>
        <div class="modal-body" align="left">Shape: <input type="text" v-model="shape" placeholder=shape/></div>
        <div class="modal-body" align="left">Rating: {{this.rating}}</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary"  data-dismiss="modal" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="provera()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Prikaz promocija i akcija -->
  <div class="modal fade" id="podaciPromocija" tabindex="-1" role="dialog" aria-labelledby="About medicine" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="AboutMedicine">About medicine</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Code: {{this.originalLeka?.medicine.code}}</div>
        <div class="modal-body" align="left">Name: {{this.originalLeka?.medicine.name}}</div>
        <div class="modal-body" align="left">Structure: {{this.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: <input type="text" v-model="manufacturer" placeholder=manufacturer/></div>
        <div class="modal-body" align="left">Note: <input type="text" v-model="note" placeholder=note/></div>
        <div class="modal-body" align="left">Type: <input type="text" v-model="type" placeholder=type/></div>
        <div class="modal-body" align="left">Points: <input type="text" v-model="points" placeholder=points/></div>
        <div class="modal-body" align="left">Price: {{this.originalLeka?.currentPrice}}</div>
        <div class="modal-body" align="left">Action/promotion: 
          <label v-if="originalLeka?.priceType=='ACTION'">Action {{100-originalLeka?.currentPrice*100/originalLeka?.oldPrice}}% (original price = {{originalLeka?.oldPrice}})</label>
          <label v-else>Promotion (original price = {{originalLeka?.oldPrice}})</label></div>
        <div class="modal-body" align="left">Quantity: <input type="text" v-model="quantity" placeholder=quantity/></div>
        <div class="modal-body" align="left">With receipt (yes/no): 
          <select id="potvrdaZahteva" style="width: 50%;" v-model="perscription" required="required">
              <option>Yes</option>
              <option>No</option>
          </select>
        </div>
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
          <h5 class="modal-title" id="Potvrdica">Are you sure you want to fire {{this.otpustiRadnika?.name}} {{this.otpustiRadnika?.surname}} ({{this.otpustiRadnika?.phoneNumber}})?</h5>
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

     <!-- brisanje Akcije ili Promocije -->
  <div class="modal fade" id="brisanjeAkcije" tabindex="-1" role="dialog" aria-labelledby="Brisanje" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Bris">Medicine {{this.originalLeka?.medicine.name}} is on action or promotion.<br/>Do you want to delete action or promotion first?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="izbrisiAkciju()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- brisanje pregleda -->
  <div class="modal fade" id="obrisiPregled" tabindex="-1" role="dialog" aria-labelledby="ObrisiPreged" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 v-if="this.izabraniPregled?.canEdit" class="modal-title" id="ObrisiNar">Are you sure you want to delete examination? </h5>
          <h5 v-else>You can't delete examination</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
          <label>&emsp; {{this.izabraniPregled?.doctor.name}} {{this.izabraniPregled?.doctor.surname}} ({{this.izabraniPregled?.doctor.phoneNumber}})</label>
          <label>&emsp; {{this.izabraniPregled?.startTime[2]}}/{{this.izabraniPregled?.startTime[1]}}/{{this.izabraniPregled?.startTime[0]}}
            {{this.izabraniPregled?.startTime[3]}}:{{this.izabraniPregled?.startTime[4]}}</label>
          <label>&emsp; Min: {{this.izabraniPregled?.durationInMins}}</label>
         <div class="modal-footer">
          <button v-if="this.izabraniPregled?.canEdit" type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="obrisiPregled()" data-dismiss="modal">Yes</button>
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
        <div class="modal-body" align="left">With receipt: 
          <select id="potvrdaZahteva" style="width: 50%;" v-model="perscription" required="required">
              <option>Yes</option>
              <option>No</option>
          </select></div>
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
          <h5 class="modal-title" id="lekic">New examination</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Start time: <input type="datetime-local" v-model="pregledStartuje"/></div>
        <div class="modal-body" align="left">Duration : <input type="number" min="1" v-model="pregledTraje"/> minutes</div>
        <div class="modal-body" align="left">Price: <input type="number" min="1" v-model="pregledKosta"/></div>
        <div class="modal-body" align="left">Choose dermatologist: 
          <select id="zamenski" style="width: 80%;" v-model="pregledDoktor" required="required">
              <option v-for="d in this.sviZaposleniDermatolozi" v-bind:value=d>{{d.name}} {{d.surname}} ({{d.phoneNumber}})</option>
          </select></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal"  data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="dodajPregled()">Make</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

        <!-- Izmeni pregled -->
  <div class="modal fade" id="izmeniPregled" tabindex="-1" role="dialog" aria-labelledby="Napravi pregled" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 v-if="this.izabraniPregled?.canEdit" class="modal-title" id="lekic">Edit appointment</h5>
          <h5 v-else>View appointment</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div v-if="this.izabraniPregled?.canEdit">
          <div class="modal-body" align="left">Start time: <input type="datetime-local" v-model="pregledStartuje"/></div>
          <div class="modal-body" align="left">Duration : <input type="number" min="1" v-model="pregledTraje"/> minutes</div>
          <div class="modal-body" align="left">Price: <input type="number" min="1" v-model="pregledKosta"/></div>
        </div><div v-else>
          <div class="modal-body" align="left">Start time: {{this.izabraniPregled?.startTime[2]}}/{{this.izabraniPregled?.startTime[1]}}/{{this.izabraniPregled?.startTime[0]}}
            {{this.izabraniPregled?.startTime[3]}}:{{this.izabraniPregled?.startTime[4]}} </div>
          <div class="modal-body" align="left">Duration : {{this.izabraniPregled?.durationInMins}} minutes</div>
          <div class="modal-body" align="left">Price: {{this.izabraniPregled?.price}}</div>
        </div>
        <div class="modal-body" align="left">Chosen dermatologist: {{this.izabraniPregled?.doctor.name}} {{this.izabraniPregled?.doctor.surname}} ({{this.izabraniPregled?.doctor.phoneNumber}})</div>
        <div class="modal-footer">
          <div v-if="this.izabraniPregled?.canEdit">
            <button type="button" class="btn btn-primary" data-dismiss="modal"  data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="izmeniPregled()">Edit</button>
          </div>
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
            pharmacyName: null, pharmacyDesc: null, pharmacyConsulting: null,
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
            upitiZaLek: [], 
        };
    },
    created() {
      this.id = AuthService.getCurrentUser().id;
      PharmacyDataService.getPharmacyByIDAdmin(this.id)
        .then(response => {
          this.pharmacy = response.data;
          this.pharmacyName = this.pharmacy.name;
          this.pharmacyDesc = this.pharmacy.description;
          this.pharmacyConsulting = this.pharmacy.pricePerHour;
          this.osveziPreglede();
        });
      this.osveziFarmaceute();
      this.osveziDermatologe();
      this.osveziLekove();
      MedicineDataService.getAllMedicines()
      .then(response => {
          this.sviLekovi = response.data;
      });

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
      osveziPreglede() {
        AppointmentDataService.getAppointmentApoteka(this.pharmacy.id)
        .then(response => {
          this.sviPreglediDermatologa = response.data;
          this.izmeniDatume();
          });   
      },

      inicijalizujPoruku(pk) { this.poruka = pk; },
      podesi(farm_der, jesteFar) { this.otpustiRadnika = farm_der; this.jesteFarmaceut = jesteFar;},
      podesiPregled(p) { 
        if (p.canEdit) {
          this.pregledTraje = p.durationInMins;
          this.pregledKosta = p.price;
        }
        this.izabraniPregled = p; 
        this.pregledStartuje = p.startTime[0] + "-" + p.startTime[1] + "-" + p.startTime[2] + "T" +
          p.startTime[3] + ":" + p.startTime[4];
      },

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
      funkcija(l, samoLek) {
        this.originalLeka = l;
        if (!samoLek) {
          this.name = l.medicine.name;
          if (l.medicine.perscription == "WITH_RECEIPT") this.perscription = "Yes";  //novi
          else this.perscription = "No";  //novi
          this.structure =  l.medicine.specification.structure;
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
      izmeniDatume() {
        for (var pregled of this.sviPreglediDermatologa) {
          var split1 = pregled.startTime.split(' ');
          var split2 = split1[0].split('-');
          var brojac = 0;
          pregled.startTime = [];
          for (var i of split2) { pregled.startTime[brojac] = i < 10 ? "0" + i : i; brojac++; }
          split2 = split1[1].split(':');
          for (var i of split2) { pregled.startTime[brojac] = i; brojac++; }
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
            if (response.data == 1) {
              this.poruka = "You successfully made appointment";
              this.osveziPreglede();
            }
            else if (response.data == -1) this.poruka = "Dermatologist doesn't work in this pharmacy in inputed time";
            else if (response.data == -2) this.poruka = "Appointment is too long, end time is after end hour of dermatologist";
            else if (response.data == -3) this.poruka = "Dermatologist already have some appointment at inputed time";
            else if (response.data == 0) this.poruka = "Dermatologist is on vacation at that day";
            return;
          });
        },
        obrisiPregled() {
          AppointmentDataService.deleteAppointmentApoteka(this.izabraniPregled)
          .then(response => {
            if (response.data == 1) this.poruka = "You can't delete examination, he was in past";
            else if (response.data == 2) this.poruka = "You can't delete examination, someone reserved him";
            else this.poruka = "You successfully deleted examination";
            this.osveziPreglede();
            return;
          });
        },
        izmeniPregled() {
          if (this.pregledTraje <= 0) {
            this.poruka = "Appointment duration must be positive number";
            return false;
          }
          if (this.pregledKosta <= 0) {
            this.poruka = "Appointment price must be positive number";
            return false;
          }
          this.izabraniPregled.startTime = this.pregledStartuje;
          this.izabraniPregled.durationInMins = this.pregledTraje;
          this.izabraniPregled.price = this.pregledKosta;
          AppointmentDataService.editAppointmentApoteka(this.izabraniPregled)
          .then(response => {
            if (response.data == 1) this.poruka = "Start time must be in future";
            else if (response.data == -1) this.poruka = "Dermatologist doesn't work in this pharmacy in inputed time";
            else if (response.data == -2) this.poruka = "Appointment is too long, end time is after end hour of dermatologist";
            else if (response.data == -3) this.poruka = "Dermatologist already have some appointment at inputed time";
            else this.poruka = "You successfully edited examination";
            this.osveziPreglede();
            return;
          });
        },
        izbrisiAkciju() {
          MedicineDataService.deleteActionPromotion(this.id, this.originalLeka.medicine.code)
            .then(response => {
              if (response.data == 0) {this.poruka = "Medicine is not found."; return;}
              else if (response.data == 1) {this.poruka = "Medicine is not on action or promotion."; return;}
              this.poruka = "Action or promotion is successfully deleted.";
              this.osveziLekove();
            });       
        },
    }
}
</script>
