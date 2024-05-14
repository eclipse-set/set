const hide = () => {
    // If the document is not loaded, retry in 50ms
    if (!document.getElementById("presentationMode")) {
        setTimeout(hide, 50)
        return
    }

    // Hide toolbar buttons
    document.getElementById("presentationMode").hidden = true
    document.getElementById("secondaryOpenFile").hidden = true
    document.getElementById("viewBookmark").hidden = true
}

hide()
