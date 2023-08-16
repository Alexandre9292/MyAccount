$("html").addClass("js");
$(function() {

  $("#main").accordion({
      objID: "#acc2", 
      obj: "div", 
      wrapper: "div", 
      el: ".h", 
      head: "h4, h5", 
      next: "div", 
      initShow : "div.shown",
      standardExpansible : true
    });

  $("#main .accordion").expandAll({
      trigger: ".h", 
      ref: "h2.country_section", 
      cllpsEl: "div.outer",
      speed: 200,
      oneSwitch : false,
      instantHide: true
  });

  $("html").removeClass("js");
});