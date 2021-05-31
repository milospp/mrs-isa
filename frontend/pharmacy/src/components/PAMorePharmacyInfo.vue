<template>
  <h3>{{this.pharmacy?.name}}</h3>
  <h5> Address: {{this.pharmacy?.address.state}}, {{this.pharmacy?.address.city}} ,
        {{this.pharmacy?.address.street}}, {{this.pharmacy?.address.number}} <br />
   Rating: {{this.pharmacy?.rating}} <br />
   Description: {{this.pharmacy?.description}} <br />
   Price per hour: {{this.pharmacy?.pricePerHour}}</h5>
 <table>
        <tr>
             <td v-if="izmene == true" align="left"> <form>
                    <input type="submit" class="btn btn-primary" v-on:click.prevent="kreirajCenovnik()" value="Make pricelist"></form> </td>
             <td v-else align="left">
                    <button type="button" class="btn btn-primary" v-on:click.prevent="inicijalizujPoruku('You can not make pricelist because you have some action or promotion')" data-toggle="modal" data-target="#obavestenje">Make pricelist</button> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
           <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make action and promotion" data-toggle="modal" data-target="#napraviAkciju"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
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
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Pricelist</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Invalid inquiries</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Orders</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Actions and promotions</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Vacation requests</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Last editing of pricelist: {{this.cenovnik?.lastEditing[1] < 10 ? "0" + this.cenovnik?.lastEditing[1] : this.cenovnik?.lastEditing[1]}}/{{this.cenovnik?.lastEditing[2] < 10 ? "0" + this.cenovnik?.lastEditing[2] : this.cenovnik?.lastEditing[2]}}/{{this.cenovnik?.lastEditing[0]}}
                  {{this.cenovnik?.lastEditing[3] < 10 ? "0" + this.cenovnik?.lastEditing[3] : this.cenovnik?.lastEditing[3]}}:{{this.cenovnik?.lastEditing[4] < 10 ? "0" + this.cenovnik?.lastEditing[4] : this.cenovnik?.lastEditing[4]}}
              </h3>
              <div v-if="this.postojiPromena == true">
                <table>
                  <tr>
                    <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                      <td> <form v-on:click.prevent="sacuvajCenovnik()"> <button type="button" class="btn btn-primary"
                          data-toggle="modal" data-target="#obavestenje">Save changes</button></form></td>
                  </tr>
                  <tr>
                    <td> &emsp; </td>
                  </tr>
                </table>
              </div>
              
              <table class="table table-striped">
                <thead class="card-header">
                  <th>Code</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Price</th>
                </thead>
                <tbody>
                  <tr :key="l" v-for="l in this.cenovnik?.medicines">
                    <td>{{l.medicine.code}}</td>
                    <td>{{l.medicine.name}}</td>
                    <td>{{l.medicine.type}}</td>
                    <td v-if="izmene == true"><input type="number" min="1" v-on:change="promenaCene(l.currentPrice, l.code)" v-model="l.currentPrice"/></td>
                    <td v-else-if="izmene == false && l.priceType != 'NORMAL'">{{l.oldPrice}}</td>
                    <td v-else>{{l.currentPrice}}</td>
                  </tr>
                </tbody>
              </table>
            </div>

            
            <div id="menu1" class="tab-pane fade">
              <h3>All invalid inquiry</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Medicine code</th>
                    <th>Medidicne name</th>
                    <th>Doctor</th>
                    <th>Doctor phone</th>
                    <th>Date and time</th>
                  </tr>
                </thead>
                <tbody>
                    <tr :key="u.name" v-for="u in this.upitiZaLek">
                      <td>{{u.medicine.code}}</td>
                      <td>{{u.medicine.name}}</td>
                      <td>{{u.doctor.name}} {{u.doctor.surname}}</td>
                      <td>{{u.doctor.phoneNumber}}</td>
                      <td>{{u.inquiryDate[1] < 10 ? "0" + u.inquiryDate[1] : u.inquiryDate[1]}}/{{u.inquiryDate[2] < 10 ? "0" + u.inquiryDate[2] : u.inquiryDate[2]}}/{{u.inquiryDate[0]}}  
                      {{u.inquiryDate[3] < 10 ? "0" + u.inquiryDate[3] : u.inquiryDate[3]}}:{{u.inquiryDate[4] < 10 ? "0" + u.inquiryDate[4] : u.inquiryDate[4]}}</td>
                    </tr>
                </tbody>
              </table>
            </div>

            <div id="menu2" class="tab-pane fade">
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

            <div id="menu3" class="tab-pane fade">
              <h3>Actions and promotions</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>Code</th>
                  <th>Name</th>
                  <th>Old rice</th>
                  <th>Action/prom</th>
                  <th>Start date</th>
                  <th>End date</th>
                  <th></th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.akcijePromocije">
                      <td>{{l.medicine.code}}</td>
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.oldPrice}}</td>
                      <!-- popust/akcija -->
                      <td v-if="l.priceType=='ACTION'">{{l.currentPrice}} ({{100-l.currentPrice*100/l.oldPrice}}%)</td>
                      <td v-else>{{l.currentPrice}}</td>
                      <td>{{l.startDate[2]}}/{{l.startDate[1]}}/{{l.startDate[2]}}</td>
                      <td>{{l.endDate[2]}}/{{l.endDate[1]}}/{{l.endDate[0]}}</td>
                      <!-- Nema promene dok je akcija/promocija i nema brisanja -->
                      <td> <form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaciPromocija">View</button></form></td>
                      <td><form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#brisanjeAkcije">Delete</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div id="menu4" class="tab-pane fade">
              <h3>Vacation requests</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>Doctor</th>
                  <th>Start date</th>
                  <th>End date</th>
                  <th>Vacation status</th>
                  <th></th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="z" v-for="z in this.zahteviZaGodisnji">
                      <td>{{z.doctor.name}} {{z.doctor.surname}}</td>
                      <td>{{z.startDate[1]}}/{{z.startDate[2]}}/{{z.startDate[0]}}</td>
                      <td>{{z.endDate[1]}}/{{z.endDate[2]}}/{{z.endDate[0]}}</td>
                      <td v-if="z.status=='WAITING'">Waiting</td>
                      <td v-else-if="z.status=='ACCEPTED'">Accepted</td>
                      <td v-else>Denied</td>
                      <td><form v-on:click.prevent="postaviZahtev(z)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaciZahtev">View</button></form></td>
                      <td v-if="z.status=='WAITING'"><form v-on:click.prevent="postaviZahtev(z)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#prihvatiZahtev">Approve</button></form></td>
                      <td v-else></td>
                  </tr>
                </tbody>
              </table>
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
        <div class="modal-body" align="left">Consulting Price: <input type="text" v-model="pharmacyConsulting" placeholder=pharmacyDesc/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#obavestenje" data-dismiss="modal" v-on:click.prevent="proveraApoteka()">Save changes</button>
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

    <!-- Promocija/akcija -->
  <div class="modal fade" id="napraviAkciju" tabindex="-1" role="dialog" aria-labelledby="poruka" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="poruka">Make action or promotion</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Choose medicine: 
          <select id="izabrani" style="width: 50%;" v-model="izabraniLek" required="required">
              <option v-for="m in this.cenovnik?.medicines" v-bind:value=m>{{m.medicine?.code}}&emsp;{{m.medicine?.name}}&emsp;{{m?.currentPrice}}USD</option>
          </select></div>
          <div class="modal-body" align="left">Old price: {{izabraniLek?.currentPrice}}</div>
          <div class="modal-body" align="left">New price: <input type="number" min="1" v-on:change="promenaProcenta()" v-model="novaCena"/></div>
          <div class="modal-body" align="left">Action discount: <input type="number" v-on:change="promenaNoveCene()" min="0" max="100" v-model="procenti"/> %</div>
          <div class="modal-body" align="left">End date of action/promotion: <input type="date" v-model="krajAkcije"/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="napraviAkciju()" data-dismiss="modal">Make</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

    <!-- Prihvatanje zahteva -->
  <div class="modal fade" id="prihvatiZahtev" tabindex="-1" role="dialog" aria-labelledby="poruka" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="poruka">Vacation request</h5>
          
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Doctor: {{this.odabraniZahtev?.doctor.name}} {{this.odabraniZahtev?.doctor.surname}} ({{this.odabraniZahtev?.doctor.phoneNumber}})</div>
        <div class="modal-body" align="left">Start date: {{this.odabraniZahtev?.startDate[1]}}/{{this.odabraniZahtev?.startDate[2]}}/{{this.odabraniZahtev?.startDate[0]}}</div>
        <div class="modal-body" align="left">End date: {{this.odabraniZahtev?.endDate[1]}}/{{this.odabraniZahtev?.endDate[2]}}/{{this.odabraniZahtev?.endDate[0]}}</div>
        <div class="modal-body" align="left">Approve: 
          <select id="potvrdaZahteva" style="width: 50%;" v-model="potvrdaZahteva" required="required">
              <option>Accept</option>
              <option>Deny</option>
          </select></div>
          <div v-if="potvrdaZahteva=='Deny'" class="modal-body" align="left">Why not: <input type="text" v-model="zastoNe"/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="obradiZahtev()" data-dismiss="modal">Save</button>
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
        <div class="modal-body" align="left">Code: {{this.izabraniLek?.medicine.code}}</div>
        <div class="modal-body" align="left">Name: {{this.izabraniLek?.medicine.name}}</div>
        <div class="modal-body" align="left">Structure: {{this.izabraniLek?.medicine.specification.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: {{this.izabraniLek?.medicine.manufacturer}}</div>
        <div class="modal-body" align="left">Note: {{this.izabraniLek?.medicine.note}}</div>
        <div class="modal-body" align="left">Type: {{this.izabraniLek?.medicine.type}}</div>
        <div class="modal-body" align="left">Points: {{this.izabraniLek?.medicine.points}}</div>
        <div class="modal-body" align="left">Price: {{this.izabraniLek?.currentPrice}}</div>
        <div class="modal-body" align="left">Action/promotion: 
          <label v-if="izabraniLek?.priceType=='ACTION'">Action {{100-izabraniLek?.currentPrice*100/izabraniLek?.oldPrice}}% (original price = {{izabraniLek?.oldPrice}})</label>
          <label v-else>Promotion (original price = {{izabraniLek?.oldPrice}})</label></div>
        <div class="modal-body" align="left">Quantity: {{this.izabraniLek?.inStock}}</div>
        <div class="modal-body" align="left">With receipt (yes/no): {{this.izabraniLek?.medicine.perscription}}</div>
        <div class="modal-body" align="left">Shape: {{this.izabraniLek?.medicine.shape}}</div>
        <div class="modal-body" align="left">Rating: {{this.izabraniLek?.rating}}</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Prikaz zahteva za godisnji -->
  <div class="modal fade" id="podaciZahtev" tabindex="-1" role="dialog" aria-labelledby="About medicine" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="AboutMedicine">About vacation</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Doctor: {{this.odabraniZahtev?.doctor.name}} {{this.odabraniZahtev?.doctor.surname}} ({{this.odabraniZahtev?.doctor.phoneNumber}})</div>
        <div class="modal-body" align="left">Start date: {{this.odabraniZahtev?.startDate[1]}}/{{this.odabraniZahtev?.startDate[2]}}/{{this.odabraniZahtev?.startDate[0]}}</div>
        <div class="modal-body" align="left">End date: {{this.odabraniZahtev?.endDate[1]}}/{{this.odabraniZahtev?.endDate[2]}}/{{this.odabraniZahtev?.endDate[0]}}</div>
        <div class="modal-body" align="left">Vacation type: 
          <label v-if="this.odabraniZahtev?.type=='REST'">Rest</label>
          <label v-else>Absence</label>
        </div>
        <div class="modal-body" align="left">Reason: {{this.odabraniZahtev?.reason}}</div>
        <div class="modal-body" align="left">Status:
          <label v-if="this.odabraniZahtev?.status=='WAITING'">Waiting</label>
          <label v-else-if="this.odabraniZahtev?.status=='ACCEPTED'">Accepted</label>
          <label v-else>Rest</label>
        </div>
        <div v-if="this.odabraniZahtev?.status!='WAITING'" class="modal-body" align="left">
          Pharmacy admin: {{this.odabraniZahtev?.pharmacyAdmin?.name}} {{this.odabraniZahtev?.pharmacyAdmin?.surname}} ({{this.odabraniZahtev?.pharmacyAdmin?.phoneNumber}})
        </div>
        <div v-if="this.odabraniZahtev?.status=='DENIED'" class="modal-body" align="left">
          Why not: {{this.odabraniZahtev?.whyNot}} </div>
        <div class="modal-footer">
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
          <h5 class="modal-title" id="Bris">Medicine {{this.izabraniLek?.medicine.name}} is on {{this.izabraniLek?.priceType == 'ACTION' ? "action" : "promotion"}}.<br/>Do you want to delete this action/promotion?</h5>
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

      <!-- obavestenja -->
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
 
</template>

<script>
import PharmacyDataService from '../service/PharmacyDataService.js';
import OrderDataService from '../service/OrderDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
import VacationDataService from '../service/VacationDataService.js';
import AuthService from "../service/AuthService.js";

export default {
    name: 'MorePharmacyInfo',
    data() {
        return {
            pharmacy: null,
            pharmacyName: null, pharmacyDesc: null, pharmacyConsulting: null,
            poruka: "Wait... Your require is in processing", 
            filterOrder: 0, narudzbenica: null, sveNarudzbenice: [], narudzbeniceZaIspis: [],
            upitiZaLek: [], postojiPromena:false, cenovnik: null, 
            izabraniLek: null, novaCena: 0, procenti: 0, krajAkcije: null, izmene: true,
            akcijePromocije: [], 
            zahteviZaGodisnji: [], odabraniZahtev: null, potvrdaZahteva: null, zastoNe: null,
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
          this.osveziUpite();
        });
      this.osveziPorudzbine();
      this.osveziCenovnik();
      this.osveziZahteve();
    },
    mounted() {  
    },
    methods : {
      osveziPorudzbine() {
        OrderDataService.getOrders(this.id)
        .then(response => {
          this.sveNarudzbenice = response.data;
          this.podesiDatume();          // da bude lepsi prikaz datuma
          this.narudzbeniceZaIspis = this.sveNarudzbenice;});   
      },
      osveziUpite() {
        PharmacyDataService.inquiriesPharmacy(this.pharmacy.id)
        .then(response => {
          this.upitiZaLek = response.data;
        });
      },
      osveziCenovnik() {
        MedicineDataService.getPricelistForPharmacyAdmin(this.id)
        .then(response => {
          this.cenovnik = response.data;
          this.akcijePromocije = []; var ind = 0;
          this.izmene = true;
          for (var prom of this.cenovnik.medicines) if (prom.priceType != "NORMAL") { this.izmene = false; this.akcijePromocije[ind] = prom; ind ++; }
        });
      },
      osveziZahteve() {
        VacationDataService.getVacationsForPharmacy(this.id)
        .then(response => {
          this.zahteviZaGodisnji = response.data;
        });
      },

      inicijalizujPoruku(pk) { this.poruka = pk; },
      postaviNarudzbenicu(p) { this.narudzbenica = p; },  // prelazak na drugu formu
      postaviLek(lek) { this.izabraniLek = lek; },
      postaviZahtev(zahtev) { this.odabraniZahtev = zahtev; },
      
      proveraApoteka() {
        if (this.pharmacyName.length == 0 || this.pharmacyDesc.length == 0 || this.pharmacyConsulting.length == 0) {
          this.poruka = "Name, description and price can't be empty.";
          return false;
        }
        this.pharmacy.name = this.pharmacyName;
        this.pharmacy.description = this.pharmacyDesc;
        this.pharmacy.pricePerHour = this.pharmacyConsulting;
        PharmacyDataService.setPharmacy(this.pharmacy)
          .then(response => {this.poruka = "You successfaly changed pharmacy info.";});
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
        this.narudzbenica.endDate[1] = parseInt(this.narudzbenica.endDate[1]);
        this.narudzbenica.endDate[2] = parseInt(this.narudzbenica.endDate[2]);
        this.narudzbenica.endDate[3] = parseInt(this.narudzbenica.endDate[3]);
        this.narudzbenica.endDate[4] = parseInt(this.narudzbenica.endDate[4]);
        this.narudzbenica.startDate[1] = parseInt(this.narudzbenica.startDate[1]);
        this.narudzbenica.startDate[2] = parseInt(this.narudzbenica.startDate[2]);
        this.narudzbenica.startDate[3] = parseInt(this.narudzbenica.startDate[3]);
        this.narudzbenica.startDate[4] = parseInt(this.narudzbenica.startDate[4]);
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
      sacuvajCenovnik() {
        for (var med of this.cenovnik.medicines) {
          if (med.currentPrice <= 0) {
            this.poruka = "All prices must be positive numbers."
            return;
          }
        }
        PharmacyDataService.savePricelist(this.id, this.cenovnik)
          .then(response => {
            this.poruka = "Pricelist is successfully edited";
            this.osveziCenovnik();
          });
      },
      promenaCene() {this.postojiPromena = true; },
      kreirajCenovnik() {
         window.location.href = "/addPricelist";
         this.osveziCenovnik();
      },
      promenaProcenta() { this.procenti = 0; },
      promenaNoveCene() { this.novaCena = this.izabraniLek?.currentPrice - this.izabraniLek?.currentPrice * this.procenti / 100; },
      napraviAkciju()  {
        if (this.izabraniLek == null) { this.poruka = "You must select some medicine."; return; }
        if (this.procenti < 0) { this.poruka = "Action discount must be positive number."; return; }
        if (this.novaCena < 0) { this.poruka = "Price for promotion must be positive number."; return; }
        if (this.procenti == 0 && this.novaCena == 0) { this.poruka = "You must enter action discount or price for promotion."; return; }
        if (this.krajAkcije == null) { this.poruka = "You must choose end date."; return; }
        MedicineDataService.makeActionPromotion(this.id, this.izabraniLek, this.procenti, this.novaCena, this.krajAkcije)
          .then(response => {
            if (response.data == -1) { this.poruka = "You don't have right to do this operation."; return; }
            if (response.data == 0) { this.poruka = "Madicine is not correct."; return; }
            if (response.data == 1) { this.poruka = "Madicine is already on some action or promotion."; return; }
            this.poruka = "You successfully made action or promotion.";
            this.osveziCenovnik();
          });
        this.izabraniLek = null;
        this.procenti = 0;
        this.novaCena = 0;
      },
      izbrisiAkciju() {
        MedicineDataService.deleteActionPromotion(this.id, this.izabraniLek.medicine.code)
          .then(response => {
            if (response.data == 0) {this.poruka = "Medicine is not found."; return;}
            else if (response.data == 1) {this.poruka = "Medicine is not on action or promotion."; return;}
            this.poruka = "Action or promotion is successfully deleted.";
            this.osveziCenovnik();
          });       
      },
      obradiZahtev() {
        if (this.potvrdaZahteva == null) { this.poruka = "You must choose to accept or deny request."; return; }
        if (this.potvrdaZahteva == "Deny" && (this.zastoNe == null || this.zastoNe?.length == 0))
          { this.poruka = "You must input reason why do not accept vacation request."; return; }
        VacationDataService.saveVacationApproval(this.id, this.odabraniZahtev, this.potvrdaZahteva, this.zastoNe)
          .then(response => {
            this.poruka = "Vacation request is successfully saved.";
            this.osveziZahteve();
          });
      },

    }
}
</script>