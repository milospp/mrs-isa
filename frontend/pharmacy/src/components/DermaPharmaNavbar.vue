<template>
    <nav v-if="role === 'dermatologist'" class="navbar navbar-default navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                Pharmacy
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li class="nav-item active"><a class="nav-link" href="#">Početna stranica</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Pacijenti</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Radni kalendar</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Lekovi</a></li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Novi pregled</a>
                </li>
            </ul>
        </div>
    </nav>
    <nav v-else-if="role === 'pharmacist'" class="navbar navbar-default navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                Pharmacy
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li class="nav-item active"><a class="nav-link" href="#">Početna stranica</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Pacijenti</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Radni kalendar</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Lekovi</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Novo savetovanje</a>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
export default {
    name: 'DermaPharmaNavbar',
    props: {
        role: String
    },
}
</script>

<style scoped>
    .navbar-nav {
        flex-direction: row;
    }
    .nav-item {
        margin: 10px;
    }
</style>