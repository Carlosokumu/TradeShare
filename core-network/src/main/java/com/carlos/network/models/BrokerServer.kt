package com.carlos.network.models

data class Broker(val servers: List<Server>)


data class Server(val name: String)