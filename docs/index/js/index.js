var supportedReleasesFilePath = "https://logitopia.github.io/jmortar-core-test/supported_releases.json"
function buildJavadocReleases() {
    $.getJSON( supportedReleasesFilePath, {
        format: "json"
      })
        .done(function( data ) {
                var output = []
                for (i=0; i < data.length; i++) {
                    var href = "javadoc/" + data[i].replace(/\./g,'_') + "/index.html"

                    if (i === 0) { output.push("<h6 class='dropdown-header'>Latest Release</h6>"); }
                    output.push("<a class=\"dropdown-item\" href=\"" + href + "\">" + data[i] + "</a>");
                    if (i == 0) { output.push("<div class='dropdown-divider'></div>") };
                }

                document.getElementById('jdoc-dropdown').innerHTML += output.join('');
        });
}

function buildTestReportReleases() {
    $.getJSON( supportedReleasesFilePath, {
        format: "json"
      })
        .done(function( data ) {
                var output = []
                for (i=0; i < data.length; i++) {
                    var href = "TestReport/" + data[i].replace(/\./g,'_') + "/index.html"

                    if (i === 0) { output.push("<h6 class='dropdown-header'>Latest Release</h6>"); }
                    output.push("<a class=\"dropdown-item\" href=\"" + href + "\">" + data[i] + "</a>");
                    if (i == 0) { output.push("<div class='dropdown-divider'></div>") };
                }

                document.getElementById('testrep-dropdown').innerHTML += output.join('');
        });
}

function buildTestCoverageReportReleases() {
    $.getJSON( supportedReleasesFilePath, {
        format: "json"
      })
        .done(function( data ) {
                var output = []
                for (i=0; i < data.length; i++) {
                    var href = "jacoco/" + data[i].replace(/\./g,'_') + "/jacocoHtml/index.html"

                    if (i === 0) { output.push("<h6 class='dropdown-header'>Latest Release</h6>"); }
                    output.push("<a class=\"dropdown-item\" href=\"" + href + "\">" + data[i] + "</a>");
                    if (i == 0) { output.push("<div class='dropdown-divider'></div>") };
                }

                document.getElementById('testcov-dropdown').innerHTML += output.join('');
        });
}