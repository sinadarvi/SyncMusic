package io.github.sinadarvi.syncmusic

enum class State {
    Server,
    Client
}

enum class FabState{
    //any of these selected, should be done next
    ShowMenuDrawer,
    Close
}

enum class Drawer{
    Locked,
    Unlocked
}