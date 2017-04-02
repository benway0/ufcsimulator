/*
 * This jQuery script deals with the checkbox tooltip, checks the main event
 * to make it 5 rounds by default, and sets the default indexes of select
 * options.
 */
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
    $("#check1").prop('checked', true);
    $("#error").attr("style", "visibility:hidden");
    $("#fighterB1").prop("selectedIndex", 1);
    $("#fighterB2").prop("selectedIndex", 1);
    $("#fighterB3").prop("selectedIndex", 1);
    $("#fighterB4").prop("selectedIndex", 1);
    $("#fighterB5").prop("selectedIndex", 1);
    $("#fighterB6").prop("selectedIndex", 1);
    $("#fighterB7").prop("selectedIndex", 1);
    $("#fighterB8").prop("selectedIndex", 1);
    $("#fighterB9").prop("selectedIndex", 1);
    $("#fighterB10").prop("selectedIndex", 1);
    $("#fighterB11").prop("selectedIndex", 1);
});

/*
 * Script to change the fighters in the select boxes based on the weight class
 * chosen.
 */
function changer(x) {
    var selectedWc = document.getElementById("fight" + x).value;
    var a = document.getElementById("fighterA" + x);
    var b = document.getElementById("fighterB" + x);
    
    switch (selectedWc) {
        case "flw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.flwList.length; i++) {
                a.options[i] = new Option(this.flwList[i].split("|")[0], this.flwList[i].split("|")[1]);
                b.options[i] = new Option(this.flwList[i].split("|")[0], this.flwList[i].split("|")[1]);
            }
            break;
        case "bw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.bwList.length; i++) {
                a.options[i] = new Option(this.bwList[i].split("|")[0], this.bwList[i].split("|")[1]);
                b.options[i] = new Option(this.bwList[i].split("|")[0], this.bwList[i].split("|")[1]);
            }
            break;
        case "fw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.fwList.length; i++) {
                a.options[i] = new Option(this.fwList[i].split("|")[0], this.fwList[i].split("|")[1]);
                b.options[i] = new Option(this.fwList[i].split("|")[0], this.fwList[i].split("|")[1]);
            }
            break;
        case "lw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.lwList.length; i++) {
                a.options[i] = new Option(this.lwList[i].split("|")[0], this.lwList[i].split("|")[1]);
                b.options[i] = new Option(this.lwList[i].split("|")[0], this.lwList[i].split("|")[1]);
            }
            break;
        case "ww":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.wwList.length; i++) {
                a.options[i] = new Option(this.wwList[i].split("|")[0], this.wwList[i].split("|")[1]);
                b.options[i] = new Option(this.wwList[i].split("|")[0], this.wwList[i].split("|")[1]);
            }
            break;
        case "mw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.mwList.length; i++) {
                a.options[i] = new Option(this.mwList[i].split("|")[0], this.mwList[i].split("|")[1]);
                b.options[i] = new Option(this.mwList[i].split("|")[0], this.mwList[i].split("|")[1]);
            }
            break;
        case "lhw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.lhwList.length; i++) {
                a.options[i] = new Option(this.lhwList[i].split("|")[0], this.lhwList[i].split("|")[1]);
                b.options[i] = new Option(this.lhwList[i].split("|")[0], this.lhwList[i].split("|")[1]);
            }
            break;
        case "hw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.hwList.length; i++) {
                a.options[i] = new Option(this.hwList[i].split("|")[0], this.hwList[i].split("|")[1]);
                b.options[i] = new Option(this.hwList[i].split("|")[0], this.hwList[i].split("|")[1]);
            }
            break;
        case "wsw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.wswList.length; i++) {
                a.options[i] = new Option(this.wswList[i].split("|")[0], this.wswList[i].split("|")[1]);
                b.options[i] = new Option(this.wswList[i].split("|")[0], this.wswList[i].split("|")[1]);
            }
            break;
        case "wbw":
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.wbwList.length; i++) {
                a.options[i] = new Option(this.wbwList[i].split("|")[0], this.wbwList[i].split("|")[1]);
                b.options[i] = new Option(this.wbwList[i].split("|")[0], this.wbwList[i].split("|")[1]);
            }
            break;
        default:
            a.options.length = 0;
            b.options.length = 0;

            for (i = 0; i < this.flwList.length; i++) {
                a.options[i] = new Option(this.flwList[i].split("|")[0], this.flwList[i].split("|")[1]);
                b.options[i] = new Option(this.flwList[i].split("|")[0], this.flwList[i].split("|")[1]);
            }
    }
    b.options[1].selected = true;
}

/*
 * Validates the form, checking that a fighter isn't fighting themselves and
 * that the same fighter doesn't appear on the card twice.
 */
function validateForm() {
    var a, b;
    var fighters = [];
    
    for (i=1,j=0; i<12; i++,j+=2) {
        a = $("#fighterA" + i).val();
        fighters[j] = a;
        b = $("#fighterB" + i).val();
        fighters[j+1] = b;
        if (a === b) {
            $("#error").attr("style", "visibility:visible");
            $("#error").html("Fighters can't fight themselves. <br />");
            return false;
        }
    }
    
    if (checkDuplicates(fighters)) {
        $("#error").attr("style", "visibility:visible");
        $("#error").html("The same fighter is on the card twice. <br />");
        return false;
    }
}

var checkDuplicates = function(a) {
    var counts = [];
    for (i=0; i<=a.length; i++) {
        if (counts[a[i]] === undefined) {
            counts[a[i]] = 1;
        } else {
            return true;
        }
    }
    return false;
};
