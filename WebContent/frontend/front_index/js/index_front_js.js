        var contextPath = $('#contextPath').val();
        var ptrnoList = $('#ptrnoList').val();

        // When the user scrolls down 50px from the top of the document, resize the header's font size
        function init() {
            console.log('init');
            var timer = '';
            window.onscroll = function() {
                if (timer !== '') {
                    clearTimeout(timer);
                    timer = '';
                }
                timer = setTimeout(function() {
                    scrollFunction();
                    if (ptrnoList !== "hasPtrnoList") {
                        scrollBottom();
                    }
                }, 100);
                searchBarUnfocus();
            };

            scrollAfterSearch();
        }
        //scroll top animation 
        function scrollFunction() {
            // console.log(document.body.scrollTop > 50 || document.documentElement.scrollTop > 50);
            if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
                document.getElementById("logoBlock").style.visibility = "hidden";
                document.getElementById("logoBlock").style.color = "rgba(255, 255, 255, 0)";
                document.getElementById("logoBlock").style.fontSize = "0px";
                document.getElementById("logoBlock").style.height = "0px";
                document.getElementById("rightHeader").style.display = "none";
                document.getElementById("leftHeader").style.display = "none";
                document.getElementById("searchBlock").style.boxShadow = "rgb(162 162 162 / 31%) 0px 3px 4px 0px";
                document.getElementById("searchBar1").style.width = "30%";
                document.getElementById("searchBar1").style.boxShadow = "inset 0 2px 4px 0 rgba(0, 0, 0, 0.10)"
                document.getElementById("fixedTop").style.position = "fixed";
                document.getElementById("searchBlock-icon").style.display = null;

            } else {
                document.getElementById("logoBlock").style.visibility = null;
                document.getElementById("logoBlock").style.fontSize = null;
                document.getElementById("logoBlock").style.color = null;
                document.getElementById("logoBlock").style.height = null;
                document.getElementById("leftHeader").style.display = null;
                document.getElementById("rightHeader").style.display = null;
                document.getElementById("searchBlock").style.boxShadow = null;
                document.getElementById("searchBlock").style.transition = null;
                document.getElementById("searchBar1").style.width = null;
                document.getElementById("searchBar1").style.boxShadow = null;
                document.getElementById("searchBlock").style.marginTop = null;
                document.getElementById("fixedTop").style = null;
                document.getElementById("searchBlock-icon").style.display = "none";

            }
        }
        //scroll loading function

        console.log("window.innerHeight" + window.innerHeight);
        console.log("document.body.offsetHeight" + document.body.offsetHeight);


        function scrollAfterSearch() {

            if ($("#getPicResult").length !== 0) {
                $('html, body').animate({
                    scrollTop: $("#getPicResult").offset().top - 400,
                }, 1000, 'swing');
            } else if ($("#errorMsgs").length !== 0) {
                console.log($("#errorMsgs").offset().top);
                $('html, body').animate({
                    // scrollTop: $("#errorMsgs").offset().top,
                    scrollTop: 650,
                }, 800, 'swing');
            }
        }


        function scrollBottom() {
            console.log('lastDiv height: ' + $('#lastDiv').offset().top);
            console.log('window.scrollY:' + window.scrollY);
            if ((window.innerHeight + window.scrollY + 1) >= $('#lastDiv').offset().top) {
                console.log('to bottom');
                loadImg10();
                setTimeout(function() {
                    loadMasonry();
                }, 500);


            }

            // $(window).scroll(function() {
            //     if ($(window).scrollTop() + $(window).height() == $(document).height()) {
            //         loadImg10();
            //         loadMasonry();
            //     }
            // });
        }

        // scroll down reload function=========================

        // ====================================================


        var rank = 15;

        function loadImg10() {
            console.log(rank);
            $.ajax({

                url: contextPath + "/painter/TagGetPic",
                method: "post",
                data: { action: "getMostLiked", rank: rank },
                success: function(data) {
                    if (data === "end") {
                        console.log('end');
                        return;
                    }
                    $(".grid").append(data);
                    rank += 15;
                },
                error: function() {
                    console.log('(index_front_js)LoadImg100 error');
                }

            })
        }
        //==========================================
        $(document).on("change", function(e) {
            loadMasonry();
        });

        //===========================================

        window.onload = () => {
            init();
            loadMasonry();
        }

        function random() {
            // var totalPic = $('#totalPic').html();
            return Math.floor(Math.random() * 7) + 1;
        }

        function loadMasonry() {
            var elem = document.querySelector('.grid');
            var msnry = new Masonry(elem, {
                // options
                itemSelector: '.grid-item',
                // columnWidth: 400,
                gutter: 25,
                fitWidth: true,

            });
            msnry.on('layoutComplete', () => {
                console.log('LayoutComplete');
            })
        }

        function toTop() {
            window.scrollTo(0, 0);
        }

        function searchBarUnfocus() {
            document.getElementById("searchBar1").blur();
        }
        // =================================================
        //=================ajax搜尋輸入模糊查詢返回推薦搜尋結果 ajax =====================
        $('#searchBar1').on("keyup", function() {
            var srtag = $(this).val();
            // alert($(this).val());
            // var url ="/controller/Search";
            $.ajax({
                method: "post",
                url: contextPath + "/painter/TagGetPic",
                data: { srtag: srtag, action: "searchByTag" },
                success: function(dataReceived) { //dataReceived=> out.println("<li> #"+result+"</li>")
                    // alert(dataReceived);
                    var bubbleJson = JSON.parse(dataReceived);
                    for (let i = 0; i <= 12; i++) {
                        var bubblei = "#bubble" + i;
                        var jsonBubblei = "bubble" + i;
                        if (bubbleJson[jsonBubblei]) {
                            $(bubblei + ">span").html(bubbleJson[jsonBubblei]);
                        } else {
                            $(bubblei + ">span").html("");
                        }
                    }
                    $("#searchList> div").html(dataReceived);
                },
                error: function() {
                    alert("(searchByTag)failed");
                }
            });
        });

        //===========bubble funciton==============================
        $('.bubble').on("click", function() {

            $(this).addClass("bubble-active");
            var BubClick = $(this).children().html();
            setTimeout(function() {
                $('#searchBar1').val(BubClick);
                console.log($('#searchBar1').val());
                $('#searchForm1').submit();
            }, 1800);

        });
        //==================================================
        ['dragover', 'dragleave', 'dragenter', 'drop'].forEach(ev => {
            window.addEventListener(ev, function(e) {
                e.preventDefault();
            })
        });

        function drag(e) {
            e.dataTransfer.setData("text", e.target.id);
        }

        function drop(e) {
            let ptrno = e.dataTransfer.getData("text");
            $('#searchBar1').css('border', '');
            $.ajax({
                method: "post",
                url: contextPath + "/painter/TagGetPic",
                data: { ptrno: ptrno, action: "picGetTag" },
                success: function(dataReceived) {
                    var bubbleJson = JSON.parse(dataReceived);
                    for (let i = 0; i <= 12; i++) {
                        var bubblei = "#bubble" + i;
                        var jsonBubblei = "bubble" + i;
                        if (bubbleJson[jsonBubblei]) {
                            $(bubblei + ">span").html(bubbleJson[jsonBubblei]);
                        } else {
                            $(bubblei + ">span").html("");
                        }
                    }
                    $("#searchList> div").html(dataReceived);
                    $('html, body').animate({
                        scrollTop: $('#BigWhiteDot').offset().top
                    }, 800);
                },
                error: function() {
                    alert("(searchByTag)failed");
                }
            });
        }

        $('#searchBar1').on('dragenter', function(e) {
            $(this).css('border', 'solid rgb(193 147 89 / 46%)');
            $(this).css('boxShadow', 'rgba(0, 0, 0, 0.1) 0px 2px 4px 0px inset, #debc8994 0px 0px 8px 1px');
            $(this).css('width', '45%');
        });

        $('#searchBar1').on('dragleave', function(e) {
            $(this).css('border', '');
            $(this).css('boxShadow', '');
            $(this).css('width', '');
        });

        function allowDrop(e) {
            e.preventDefault();
        }

        //================================
        // $('.grid-item').on('click', function(e) {

        //     $('#modalButton').click();
        // })

        // Get the modal

        // Get the image and insert it inside the modal - use its "alt" text as a caption

        $('.grid').on('click', function(e) {
            if ($(e.target).parents('.grid-item').length) {

                $("#myModal").css('display', 'block');
                $("#img01").attr('src', e.target.src);
                //      $("#img01-a").attr('href', contextPath + '/frontend/painter/onePainter.jsp?ptr_no=' + e.target.id + '"');
                $("#img01-a").attr('href', contextPath + '/frontend/painter/onePainter.jsp?ptr_no=' + e.target.id);

                $("#img01").after('<input type="hidden" name="ptrno" value="' + e.target.id + '">');
                let ptrno = e.target.id;

                $.ajax({
                    method: "post",
                    url: contextPath + "/painter/TagGetPic",
                    data: { ptrno: ptrno, action: "msgUpdate" },
                    success: function(d) {
                        $('#msg-content').html(d);
                        $('.comment-textarea').before('<input type="hidden" name="ptrno" value="' + ptrno + '"');

                    },
                    error: function() {
                        alert("(msgUpdate)failed");
                    }
                });
            }
        });

        $('#myModal').on('click', function(e) {
            var target = $(e.target);
            if (!target.parents('.myContainer').length) {
                $('#myModal').css('display', 'none');
            }
        });
        // ============================================================

        $('.comment-send').on('click', function(e) {
            e.preventDefault();

            var data = $('#writeComment').serialize();
            var ptrno = $('#img01').next().val();
            console.log("data" + data);
            $.ajax({
                method: "post",
                url: contextPath + "/painter/TagGetPic",
                data: data + "&ptrno=" + ptrno,
                success: function(d) {
                    $.ajax({
                        method: "post",
                        url: contextPath + "/painter/TagGetPic",
                        data: { ptrno: ptrno, action: "msgUpdate" },
                        success: function(d) {
                            $('#msg-content').html(d);
                            $('.comment-textarea').before('<input type="hidden" name="ptrno" value="' + ptrno + '"');
                            $('#commentInput').val('');
                            $('#msg-content').animate({
                                scrollTop: $('#msg-content')[0].scrollHeight
                            }, 0);
                        },
                        error: function() {
                            alert("(msgUpdate)failed");
                        }
                    });
                },
                error: function() {
                    alert("(msgUpdate)failed");
                }
            });
        })

        $('#searchBlock-icon').on('click', function() {
            window.scrollTo(0,0);
            window.location.href=contextPath + "/frontend/front_index.jsp";

        });